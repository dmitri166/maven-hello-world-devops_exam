package com.myapp;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Test that App prints the expected Hello World message
     */
    @Test
    public void testAppOutput()
    {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        App.main(new String[]{});
        
        String output = outContent.toString();
        assertTrue("Should contain Hello World message", 
                   output.contains("Hello World! - Dmitri Kachka"));
    }
}
