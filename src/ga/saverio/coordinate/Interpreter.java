package ga.saverio.coordinate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

public class Interpreter {
    public enum TokenType {
       INSTRUCTION, NUMBER, STRING
    }
    public static class Token {
        public Token(String text, TokenType type) {
            this.text = text;
            this.type = type;
        }
        
        public final String text;
        public final TokenType type;
    }
    private enum TokenizeState {
        DEFAULT, NUMBER, STRING
    }
    TokenizeState state = TokenizeState.DEFAULT;
    public List<Token> tokenize(String data) {
    	List<Token> tokens = new ArrayList();
    	data += "<EOF>";
    	String operatorChars = "\n=+-*/<>()";
    	String token = "";
    	for (int i = 0; i < data.length(); i++) {
    		char c = data.charAt(i);
    		switch (state) {
    		case DEFAULT:
    			if (token.equals("PRINT")) {
    				tokens.add(new Token("print", TokenType.INSTRUCTION));
    				token = "";
    			} else if (c == ' ') {
    				token = "";
    			} else if (c == '"') {
    				state = TokenizeState.STRING;
    			} else if (Character.isLetter(c)) {
    				token += c;
    			} else if (Character.isDigit(c)) {
    				token += c;
    				state = TokenizeState.NUMBER;
    			}
    			System.out.println(token);
    			break;
    		case STRING:
    			if (!(c == '"')) {
    				token += c;
    			} else {
    				tokens.add(new Token(token, TokenType.STRING));
    				token = "";
    				state = TokenizeState.DEFAULT;
    			}
    			break;
    		case NUMBER:
    			if (Character.isDigit(c)) {
    				token += c;
    			} else {
    				tokens.add(new Token(token, TokenType.NUMBER));
    				token = "";
    				state = TokenizeState.DEFAULT;
    			}
    			break;
    			
    		}
    		
    	}
    	return tokens;
    }
    public void parser(List<Token> tokens) {
    	for (int i = 0; i < tokens.size(); i++) {
    		if (tokens.get(i).type == TokenType.INSTRUCTION) {
    			if (tokens.get(i).text.equals("print")) {
    				Bukkit.getServer().broadcastMessage(tokens.get(i+1).text);
    			}
    		}
    	}
    }
}
