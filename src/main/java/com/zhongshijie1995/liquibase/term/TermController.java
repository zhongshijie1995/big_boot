package com.zhongshijie1995.liquibase.term;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 操作系统类
 */
public class TermController {
    // 操作系统环境
    private static final String WIN = "win";
    private static final String UNIX = "unix";
    private static final String WIN_CLI = "powershell.exe";
    private static final String UNIX_CLI = "/bin/sh";

    // 辅助内容
    private static final String I = "CLI";

    // 日志编辑类
    private static Logger logger;

    /**
     * 操作系统控制器
     */
    public TermController() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * 获取操作系统
     *
     * @return 操作系统简称
     */
    public String getOs() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.startsWith(WIN) ? WIN : UNIX;
    }

    /**
     * 获取终端程序
     *
     * @return 终端程序路径
     */
    public String getOsCli() {
        return getOs().equals(WIN) ? WIN_CLI : UNIX_CLI;
    }

    /**
     * 获取编码字符集
     *
     * @return 字符集
     */
    public String getOsEncoding() {
        return Charset.defaultCharset().toString();
    }

    /**
     * 拼接命令
     *
     * @param head   命令头
     * @param params 命令参数
     * @return 拼接后命令
     */
    public String appendCmd(String head, List<String> params) {
        StringBuilder cmd = new StringBuilder();
        cmd.append(head);
        for (String param : params) {
            cmd.append(" ");
            cmd.append(param);
        }
        return cmd.toString();
    }

    /**
     * 在终端执行命令
     *
     * @param path 执行路径
     * @param cmd  执行命令
     */
    public void runCmd(String path, String cmd) {
        logger.info("[{}] 执行命令 {}", I, cmd);
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder
                    .directory(new File(path).getAbsoluteFile())
                    .command(getOsCli(), "-c", cmd)
                    .redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), getOsEncoding()));
            String line;
            boolean needShowLog = false;
            while ((line = br.readLine()) != null) {
                if (needShowLog) {
                    logger.info(line);
                }
                if (line.contains("Liquibase Community 4.12.0 by Liquibase")) {
                    needShowLog = true;
                }
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
