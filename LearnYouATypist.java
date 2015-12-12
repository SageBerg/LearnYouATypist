import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LearnYouATypist 
{
    private static int CRLF_SYMBOL = 182;
    private static int LINE_LENGTH = 80;
    private static double WORD_LENGTH = 5.0;
    private static double MINUTE = 60000.0;

    public static void main (String args[]) 
    {

        Scanner scanner = new Scanner(System.in);
        String prompt;
        String response;

        int characters_typed = 0;
        long start_time = System.currentTimeMillis();
        long stop_time;
        long time_taken;
        double words_per_minute = 0.0;
        FileInputStream stream = null;
        ArrayList<String> error_comparisons = new ArrayList<String>();

        try {
            stream = new FileInputStream("test.txt"); 
            while (stream.available() > 0)
            {
                prompt = output_line(stream);
                response = scanner.nextLine() + '\n';
                characters_typed += response.length();
                error_comparisons = add_to_error_comparisons(prompt,
                                                             response,
                                                             error_comparisons);
                System.out.println();
            }
            stop_time = System.currentTimeMillis();
            time_taken = stop_time - start_time;

            double time = (double) time_taken;
            double chars = (double) characters_typed;
            int errors = error_comparisons.size() / 2;
            words_per_minute = (chars / WORD_LENGTH) / (time / MINUTE);

            System.out.println("words per minute: " + words_per_minute);
            System.out.println("error rate: " +
                               ((double) errors) / (chars / WORD_LENGTH));
            System.out.println("mistakes: " + errors);
            if (error_comparisons.size() > 0) {
                System.out.println("summary of mistakes: ");
                for (int i = 0; i < error_comparisons.size(); i += 2) {
                    System.out.println(error_comparisons.get(i));
                    System.out.println(error_comparisons.get(i + 1));
                }
            }

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
                System.out.print((char) CRLF_SYMBOL);
            }
            System.out.print(c);
            prompt += c;
            if (c == '\n') {
                break;
            }
            count += 1;
        }
        if (c != '\n') {
            System.out.println((char) CRLF_SYMBOL);
            prompt += '\n';
        }
        return prompt;
    }

    //returns a list of words typed incorrectly so the user can see what kinds
    //  mistakes they are making
    private static ArrayList<String> add_to_error_comparisons(String prompt,
                                                       String response,
                                                       ArrayList<String> list)
    {
        String[] split_prompt = prompt.split(" "); 
        String[] split_response = response.split(" "); 
        for (int i = 0; i < Math.min(split_prompt.length, split_response.length); i++) 
        {
            if (!split_prompt[i].equals(split_response[i])) {
                list.add(split_prompt[i]);
                list.add(split_response[i]);
            }
        }
        return list;
    }

}
