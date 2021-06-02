public class SHA256 {
    // Private Constants
    private static int h0 = 0x6a09e667;
    private static int h1 = 0xbb67ae85;
    private static int h2 = 0x3c6ef372;
    private static int h3 = 0xa54ff53a;
    private static int h4 = 0x510e527f;
    private static int h5 = 0x9b05688c;
    private static int h6 = 0x1f83d9ab;
    private static int h7 = 0x5be0cd19;

    private static final int[] k = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2};


    public static byte[] encode(byte[] data) {
        int msgLength = data.length; //length in bytes
        long msgBitLength = msgLength * 8L;  //length in bits

        byte[] withOne = new byte[msgLength+1];
        System.arraycopy(data, 0, withOne, 0, msgLength);
        withOne[withOne.length - 1] = (byte) 0x80;    //append byte with leading 1-bit (1000 0000)

        int k = withOne.length*8;  // message length in bits. This needs to be filled into the trailing 64 bit of the last block


        while (k % 512 != 448) {
            k += 8;
        }

        //size of block with appended zeros
        byte[] withZero = new byte[k/8];
        System.arraycopy(withOne, 0 , withZero, 0, withOne.length);

        //add 64 bits for original length
        byte[] output = new byte[withZero.length + 8];

        for (int i = 0; i < 8; i++) {
            // highest index in the array gets the lowest value bit
            // shift right i byte and truncate the rightmost byte
            output[output.length -1 - i] = (byte) ((msgBitLength >>> (8 * i)) & 0xFF);
        }
        System.arraycopy(withZero, 0 , output, 0, withZero.length);

        int numChunks = output.length * 8 /512;
        for (int i = 0; i < numChunks; i++) {
            int[] w = new int[64];

            buildWord(output, i, w);

            //initialize working variables
            int a = h0;
            int b = h1;
            int c = h2;
            int d = h3;
            int e = h4;
            int f = h5;
            int g = h6;
            int h = h7;

            for (int j = 0; j < 64; j++) {
                int S1 = rightRotate(e, 6) ^ rightRotate(e, 11) ^ rightRotate(e, 25);
                int ch = (e & f) ^ (~e & g);
                int temp1 = h + S1 + ch + SHA256.k[j] + w[j];
                int S0 = rightRotate(a, 2) ^ rightRotate(a, 13) ^ rightRotate(a, 22);
                int maj = (a & b) ^ (a & c) ^ (b & c);
                int temp2 = S0 + maj;

                h = g;
                g = f;
                f = e;
                e = d + temp1;
                d = c;
                c = b;
                b = a;
                a = temp1 + temp2;
            }

            //Update hash values
            h0 = h0 + a;
            h1 = h1 + b;
            h2 = h2 + c;
            h3 = h3 + d;
            h4 = h4 + e;
            h5 = h5 + f;
            h6 = h6 + g;
            h7 = h7 + h;
        }

        // Finalize the hash value
        // Concatenate the hash values to one 256-bit output byte[]
        byte[] hash = new byte[32];

        for (int j = 0; j < 4; j++) {
            hash[j] = (byte) ((h0 >>> 24-j*8) & 0xFF);
        }
        for (int j = 0; j < 4; j++) {
            hash[j+4] = (byte) ((h1 >>> 24-j*8) & 0xFF);
        }
        for (int j = 0; j < 4; j++) {
            hash[j+8] = (byte) ((h2 >>> 24-j*8) & 0xFF);
        }
        for (int j = 0; j < 4; j++) {
            hash[j+12] = (byte) ((h3 >>> 24-j*8) & 0xFF);
        }
        for (int j = 0; j < 4; j++) {
            hash[j+16] = (byte) ((h4 >>> 24-j*8) & 0xFF);
        }
        for (int j = 0; j < 4; j++) {
            hash[j+20] = (byte) ((h5 >>> 24-j*8) & 0xFF);
        }
        for (int j = 0; j < 4; j++) {
            hash[j+24] = (byte) ((h6 >>> 24-j*8) & 0xFF);
        }
        for (int j = 0; j < 4; j++) {
            hash[j+28] = (byte) ((h7 >>> 24-j*8) & 0xFF);
        }
        return hash;
    }


    private static int rightRotate(int n, int d) {
        return (n >>> d) | (n << (32 - d));
    }

    /*
     * Build 64 W[] words for each M {0,1} block of 512 bytes. 16 first are just 32i bytes of the msg, then 17 to 64 are build
     * using smallSigma function 0 and 1, and previous results
     */
    public static void buildWord(byte[] output, int i, int[] w) {

            //divide chunk into 16 words
            for (int j = 0; j < 16; j++) {
                w[j] =  ((output[i*512/8 + 4*j] << 24) & 0xFF000000) | ((output[i*512/8 + 4*j+1] << 16) & 0x00FF0000);
                w[j] |= ((output[i*512/8 + 4*j+2] << 8) & 0xFF00) | (output[i*512/8 + 4*j+3] & 0xFF);
            }

            for (int j = 16; j < 64; j++) {
                int s0 = rightRotate(w[j-15], 7) ^ rightRotate(w[j-15], 18) ^ (w[j-15] >>> 3);
                int s1 = rightRotate(w[j-2], 17) ^ rightRotate(w[j-2], 19) ^ (w[j-2] >>> 10);
                w[j] = w[j-16] + s0 + w[j-7] + s1;
            }
    }

}