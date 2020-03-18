package regex;

import errors.ParseError;

//Source : slides of lecture 3, page 44;
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
