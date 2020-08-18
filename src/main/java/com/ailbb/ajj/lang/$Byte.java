package com.ailbb.ajj.lang;

/**
 * Created by Wz on 8/18/2020.
 */
public class $Byte {
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public String toHexString(byte[] var0) {
        String var1 = "";

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1 = var1 + HEX_CHARS[var0[var2] >>> 4 & 15];
            var1 = var1 + HEX_CHARS[var0[var2] & 15];
        }

        return var1;
    }

    public String toHexString(byte[] var0, String var1, String var2) {
        String var3 = new String(var1);

        for(int var4 = 0; var4 < var0.length; ++var4) {
            var3 = var3 + HEX_CHARS[var0[var4] >>> 4 & 15];
            var3 = var3 + HEX_CHARS[var0[var4] & 15];
            if (var4 < var0.length - 1) {
                var3 = var3 + var2;
            }
        }

        return var3;
    }


    public static byte[] clone(byte[] var0) {
        if (var0 == null) {
            return null;
        } else {
            byte[] var1 = new byte[var0.length];
            System.arraycopy(var0, 0, var1, 0, var0.length);
            return var1;
        }
    }

    public static byte[] fromHexString(String var0) {
        char[] var1 = var0.toUpperCase().toCharArray();
        int var2 = 0;

        for(int var3 = 0; var3 < var1.length; ++var3) {
            if (var1[var3] >= '0' && var1[var3] <= '9' || var1[var3] >= 'A' && var1[var3] <= 'F') {
                ++var2;
            }
        }

        byte[] var6 = new byte[var2 + 1 >> 1];
        int var4 = var2 & 1;

        for(int var5 = 0; var5 < var1.length; ++var5) {
            if (var1[var5] >= '0' && var1[var5] <= '9') {
                var6[var4 >> 1] = (byte)(var6[var4 >> 1] << 4);
                var6[var4 >> 1] = (byte)(var6[var4 >> 1] | var1[var5] - 48);
            } else {
                if (var1[var5] < 'A' || var1[var5] > 'F') {
                    continue;
                }

                var6[var4 >> 1] = (byte)(var6[var4 >> 1] << 4);
                var6[var4 >> 1] = (byte)(var6[var4 >> 1] | var1[var5] - 65 + 10);
            }

            ++var4;
        }

        return var6;
    }



    /*
     * 将二进制转换成16进制
     */
    public String parseByte2HexStr(byte buf[]) {
        return toHexString(buf);
    }

    /*
     * 将16进制转换为二进制
     */
    public byte[] parseHexStr2Byte(String hexStr) {
        return fromHexString(hexStr);
    }

}
