package regex;


import regex.Alphabet;

public class Regex {
	class Lexer {

		String[] input;
		int idx = 0;

		Lexer(String[] input) {
			this.input = input;
		}

		public void mustBe(String t) throws Exception {
	        if(idx < input.length && t.equals(this.token())){
	            advance();
	        }else{
	            throw new Exception("error mustBe("+ t + ")");
	        }
	    }

		public void advance() {
			idx++;
		}

		public String token() throws Exception {
			if(idx >= input.length){
	            throw new Exception("error token() " + idx);
	        }
			return input[idx];
		}

		public boolean done() {
			return input.length <= idx;
		}
	}
	
	//modify this 
	public boolean parse(String[] input){
		try {
			Lexer lex = new Lexer(input);
			parser0(lex);
			return lex.done();
		}catch (Exception e){
			//System.out.println(e.getMessage());
			return false;
		}
	}
	
	private void parser0(Lexer lex) throws Exception{
		if (lex.token().equals(Alphabet.A)){
			lex.mustBe(Alphabet.A);
			parser1(lex);
		}
		else if(lex.token().equals(Alphabet.C)){
			lex.mustBe(Alphabet.C);
			parser2(lex);
			
		}
		else {
			throw new Exception();
		}
	}
	
	private void parser1(Lexer lex) throws Exception{
		if(!lex.done() && lex.token().equals(Alphabet.A)){
			lex.mustBe(Alphabet.A);
			parser7(lex);
			
		}
		else if(!lex.done() && lex.token().equals(Alphabet.B)){
			lex.mustBe(Alphabet.B);
			parser1(lex);
			
		}
		else if(!lex.done() && lex.token().equals(Alphabet.C)){
			lex.mustBe(Alphabet.C);
			parser3(lex);
			
		}
		else {
			return;
		}
	}
	private void parser2(Lexer lex) throws Exception{
		return;
	}
	private void parser3(Lexer lex) throws Exception{
		if(!lex.done() && lex.token().equals(Alphabet.C)){
			lex.mustBe(Alphabet.C);
			parser3(lex);
			
		}
		else {
			return;
		}
	}
	private void parser7(Lexer lex) throws Exception{
		if(lex.token().equals(Alphabet.B)){
			lex.mustBe(Alphabet.B);
			parser8(lex);
			
		}
		else {
			throw new Exception();
		}
	}
	
	private void parser8(Lexer lex) throws Exception{
		if (lex.token().equals(Alphabet.A)){
			lex.mustBe(Alphabet.A);
			parser7(lex);
		}
		else if(lex.token().equals(Alphabet.C)){
			lex.mustBe(Alphabet.C);
			parser2(lex);
			
		}
		else {
			throw new Exception();
		}
	}
	
}