import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;

public class LearnYouATypist 
{

    private static int LINE_LENGTH = 80;
    private static double WORD_LENGTH = 5.0;
    private static double MINUTE = 60000.0;

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
        double words_per_minute = 0.0;
        FileInputStream stream = null;

        try {
            stream = new FileInputStream("test.txt"); 
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

            double time = (double) time_taken;
            double chars = (double) characters_typed;
            words_per_minute = (chars / WORD_LENGTH) / (time / MINUTE);

            System.out.println("errors: " + errors);
            System.out.println("error rate: " + ((double) errors) / chars);
            System.out.println("words per minute: " + words_per_minute);

        } catch (Exception e) {
            e.printStackTrace();            
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static String output_line(FileInputStream stream) throws IOException
    {
        char c = ' ';
        int i;
        int count = 0;
        String prompt = "";
        while ((count <= LINE_LENGTH || !Character.isWhitespace(c))
               && (i = stream.read()) != -1)
        {
            c = (char) i;
            if (c == '\n') {
                System.out.print((char) 182);
            }
            System.out.print(c);
            prompt += c;
            if (c == '\n') {
                break;
            }
            count += 1;
        }
        if (c != '\n') {
            System.out.println((char) 182);
            prompt += '\n';
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
