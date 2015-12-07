import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;

public class LearnYouATypist 
{

    private static int LINE_LENGTH = 80;
    private static long WORD_LENGTH = 5;
    private static long MINUTE = 60000;
    
    public static void main (String args[]) 
    {
        Scanner scanner = new Scanner(System.in);
        String prompt;
        String response;

        int characters_typed = 0;
        int errors = 0;
        long start_time = System.currentTimeMillis();
        long stop_time;
        long time_taken;
        long words_per_minute = 0;

        try {
            FileInputStream stream = new FileInputStream("test.txt"); 
            while (stream.available() > 0)
            {
                prompt = output_line(stream);
                response = scanner.nextLine() + '\n';
                characters_typed += response.length();
                errors += count_errors(prompt, response);
                System.out.println();
            }
            stop_time = System.currentTimeMillis();
            time_taken = stop_time - start_time;
            System.out.println(time_taken);
            words_per_minute = (characters_typed / WORD_LENGTH) / (time_taken / MINUTE);
            System.out.println(errors + " errors");
            System.out.println(words_per_minute + " words_per_minute");
            
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
