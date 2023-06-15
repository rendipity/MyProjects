package com.mySpring.core.io;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ClassPathResource implements Resource{

    private final String filePath;

    public ClassPathResource(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream(this.filePath);
        if (resourceStream == null)
            throw new FileNotFoundException(this.filePath+" is not exist");
        return resourceStream;
    }
}
