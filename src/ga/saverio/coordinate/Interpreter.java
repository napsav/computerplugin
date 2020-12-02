package ga.saverio.coordinate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;

public class Interpreter {
    public enum TokenType {
       NULL, INSTRUCTION, NUMBER, STRING
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
    			} if (token.equals("REPEAT")) {
    				tokens.add(new Token("repeat", TokenType.INSTRUCTION));
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
    public void print(Token tok) {
    	Bukkit.getServer().broadcastMessage(tok.text);
    }
    private enum ErrorType {
    	SYNTAX, MISSING_TOKEN,
    }
    public void error(ErrorType error) {
    	Bukkit.getServer().broadcastMessage(ChatColor.RED + "[CraftBASIC Interpreter Error] " + error + " error");
    }
    private class Variable {
    	public TokenType type = TokenType.NULL;
    	public String text = null;
    	public int value = 0;
    	public Variable(Token tok) {
    		this.type = tok.type;
    		if (this.type == TokenType.NUMBER) {
    			int value = Integer.parseInt(tok.text);
    		} else {
    		this.text = tok.text;
    		}
    	}
    	
    }
    public void parser(List<Token> tokens) {
    	for (int i = 0; i < tokens.size(); i++) {
    		if (tokens.get(i).type == TokenType.INSTRUCTION) {
    			if (tokens.get(i).text.equals("print")) {
    				print(tokens.get(i+1));
    			} else if (tokens.get(i).text.equals("repeat")) {
    				if (tokens.get(i+1).type == TokenType.NUMBER) {
    					for (int j = 0; j < Integer.parseInt(tokens.get(i+1).text )- 1; j++) {
    						if (tokens.get(i+2).type == TokenType.INSTRUCTION && tokens.get(i+2).text.equals("print") && tokens.get(i+3).type == TokenType.STRING) {
    							print(tokens.get(i+3));
    						} else {
    							error(ErrorType.MISSING_TOKEN);
    						}
    					}
    				} else {
    					error(ErrorType.SYNTAX);
    				}
    			}
    		}
    	}
    }
}
