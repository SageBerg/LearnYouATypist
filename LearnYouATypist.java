import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;

public class LearnYouATypist 
{

    private static int LINE_LENGTH = 80;
    
    public static void main (String args[]) 
    {
        Scanner scanner = new Scanner(System.in);
        String prompt;
        String response;

        try {
            FileInputStream stream = new FileInputStream("test.txt"); 
            while (stream.available() > 0)
            {
                prompt = output_line(stream);
                response = scanner.nextLine() + '\n';
                System.out.println(count_errors(prompt, response));
            }
        } catch (Exception e) {
            e.printStackTrace();            
        } finally {
            /*
            if (stream != null) {
                stream.close();
            }
            */
        }
    }

    private static String output_line(FileInputStream stream) throws IOException
    {
        char c;
        int i;
        int count = 0;
        String prompt = "";
        while ((i = stream.read()) != -1 && count <= LINE_LENGTH)
        {
            c = (char) i;
            System.out.print(c);
            prompt += c;
            if (c == '\n') {
                break;
            }
            count += 1;
        }
        return prompt;
    }

    private static int count_errors(String prompt, String response)
    {
        int errors = 0;
        for (int i = 0; i < Math.min(prompt.length(), response.length()); i++) 
        {
            if (prompt.charAt(i) != response.charAt(i)) {
                errors += 1;
            }
        }
        errors += Math.abs(prompt.length() - response.length());
        return errors;
    }

}
