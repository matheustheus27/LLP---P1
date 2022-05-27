import LEXICAL.LexicalAnalysis;
import SYNTATIC.SyntaticAnalysis;

public class isql {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java isql [iSQL file]");
            return;
        }

        try (LexicalAnalysis l = new LexicalAnalysis(args[0])) {
            SyntaticAnalysis s = new SyntaticAnalysis(l);

            s.Start();
            
            System.out.println("Sim");

        } catch (Exception e) {
            System.err.println("Não");
        }   
    }
}
