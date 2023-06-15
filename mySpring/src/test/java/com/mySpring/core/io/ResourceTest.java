package com.mySpring.core.io;

import cn.hutool.core.io.IoUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ResourceTest {

    @Test
    public void testResource(){
        DefaultResourceLoader defaultResourceLoader =new DefaultResourceLoader();
        // 加载 classPath file
        Resource classPathResource = defaultResourceLoader.getResource("classpath:classPathFile.txt");
        InputStream classPathInputStream = null;
        try {
            classPathInputStream = classPathResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content1 = IoUtil.readUtf8(classPathInputStream);
        System.out.println(content1);


        // 加载 系统文件
        Resource systemFileResource = defaultResourceLoader.getResource("/Users/chenxinyu/Daily/JAVA/ProjectRepository/mySpring/src/test/resources/systemPathFile.txt");
        InputStream systemInputStream = null;
        try {
            systemInputStream = systemFileResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content2 = IoUtil.readUtf8(systemInputStream);
        System.out.println(content2);

        // 加载 系统文件
        Resource RemoteFileResource = defaultResourceLoader.getResource("https://www.baidu.com");
        InputStream remoteInputStream = null;
        try {
            remoteInputStream = RemoteFileResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content3 = IoUtil.readUtf8(remoteInputStream);
        System.out.println(content3);

    }
}
