package com.ruffneck.mobilesafer.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 佛剑分说 on 2015/10/8.
 */
public class StreamUtils {


    /**
     * 将输入流的数据读取成String后然后返回
     * @param is
     * @return
     * @throws IOException
     */
    public static String readFromStream(InputStream is) throws IOException {
        String str;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();


        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }

        is.close();
        str = bos.toString();
        bos.close();

        return str;
    }
}
