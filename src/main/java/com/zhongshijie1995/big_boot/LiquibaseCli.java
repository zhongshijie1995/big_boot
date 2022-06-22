package com.zhongshijie1995.big_boot;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LiquibaseCli {

    // 操作系统环境
    public static final String WIN_CLI = "cmd.exe";
    public static final String UNIX_CLI = "/bin/sh";
    public static final String BASE_PATH = "db";
    public static final String LIQUIBASE = "./liquibase/liquibase";
    public static final String SPACE = " ";

    public static void main(String[] args) {
        List<String> params = new ArrayList();
        params.add("rollbackCount 1");
        runCmd(BASE_PATH, getCmd(params));
    }

    public static String getCmd(List<String> params) {
        StringBuilder cmd = new StringBuilder();
        cmd.append(LIQUIBASE);
        cmd.append(SPACE);
        for (String param: params) {
            cmd.append(param);
            cmd.append(SPACE);
        }
        return cmd.toString();
    }

    public static String getOsCli() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.startsWith(WIN_CLI)) {
            return WIN_CLI;
        }
        return UNIX_CLI;
    }

    public static void runCmd(String path, String cmd) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(getOsCli(), "-c", cmd);
        processBuilder.directory(new File(path).getAbsoluteFile());
        processBuilder.redirectErrorStream(true);
        try {
            Process start = processBuilder.start();
            InputStream inputStream = start.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            int len;
            char[] c = new char[1024];
            StringBuffer outputString = new StringBuffer();
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                outputString.append(s);
                System.out.print(s);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
