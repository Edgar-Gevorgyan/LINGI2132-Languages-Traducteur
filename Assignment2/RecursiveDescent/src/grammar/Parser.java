package grammar;



import static grammar.Grammar.*;

public class Parser {

	private class Lexer{
		String [] input;
		int idx = 0;

		Lexer(String[] input){
			this.input = input;
		}

		void mustBe(String t) throws Exception {
			if(idx < input.length && t.equals(this.token())){
				advance();
			}else{
				throw new Exception("error mustBe("+ t + ")");
			}
		}

		void advance(){
			idx++;
		}

		String token() throws Exception {
			if(idx >= input.length){
				throw new Exception("error token() " + idx);
			}
			return input[idx];
		}

		boolean done(){
			return input.length == idx;
		}
	}

	//modify this  
	public boolean parse(String[] input) {
		try {
			Lexer lex = new Lexer(input);
			parserE(lex);
			return lex.done();
		}catch (Exception e){
			//System.out.println(e.getMessage());
			return false;
		}
	}

	private void parserE(Lexer lex) throws Exception {
		parserT(lex);
		parserEprime(lex);
	}

	private void parserEprime(Lexer lex) throws Exception {
		if(!lex.done() && lex.token().equals(OR)){
			lex.mustBe(OR);
			parserT(lex);
			parserEprime(lex);
		}
	}

	private void parserT(Lexer lex) throws Exception {
		parserF(lex);
		parserTprime(lex);
	}

	private void parserTprime(Lexer lex) throws Exception {
		if(!lex.done() && lex.token().equals(AND)){
			lex.mustBe(AND);
			parserF(lex);
			parserTprime(lex);
		}
	}

	private void parserF(Lexer lex) throws Exception {
		switch (lex.token()) {
			case NOT:
				lex.mustBe(NOT);
				parserF(lex);
				break;
			case LEFTPAR:
				lex.mustBe(LEFTPAR);
				parserE(lex);
				lex.mustBe(RIGHTPAR);
				break;
			default:
				lex.mustBe(ID);
		}

	}
}
