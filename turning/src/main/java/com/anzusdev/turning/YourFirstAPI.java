package com.anzusdev.turning;

import java.util.ArrayList;

import com.anzusdev.turning.businesslogic.HandleKey;
import com.anzusdev.turning.util.CipherAndKey;
import com.anzusdev.turning.util.Message;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;


@Api(name = "turning", version = "v1",
		scopes = {Constants.EMAIL_SCOPE },
		clientIds = {Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID },
		description = "API for turning grille cipher.")

public class YourFirstAPI {

	@ApiMethod(name = "getRotations", path = "getRotations", httpMethod = HttpMethod.GET)
	public Message getRotations(@Named("n") int n, @Named("position") int pos) {
		HandleKey handleKey = new HandleKey();		
		Message response = new Message();
		response.setData( handleKey.rotateItem(n, pos).toString() );
		return response;
	}
	
	@ApiMethod(name = "generateKey", path = "generateKey", httpMethod = HttpMethod.GET)
	public Message generateKey(@Named("n") int n){
		HandleKey handleKey = new HandleKey();	
		Message response = new Message();
		response.setData( handleKey.generateKey(n).toString() );
		return response;
	}
	
	@ApiMethod(name = "getN", path = "getN", httpMethod = HttpMethod.GET)
	public Message getN(@Named("plaintext") String m){
		HandleKey handleKey = new HandleKey();	
		Message response = new Message();
		response.setData( String.valueOf( handleKey.getN(m) ) );
		return response;
	}
	
	@ApiMethod(name = "getCipherText", path = "getCipherText", httpMethod = HttpMethod.GET)
	public Message getCipherText(@Named("plaintext") String m){
		HandleKey handleKey = new HandleKey();	
		Message response = new Message();
		response.setData( handleKey.getCipherText(m) );
		return response;
	}
	
	@ApiMethod(name = "getCipherTextWithKey", path = "getCipherTextWithKey", httpMethod = HttpMethod.GET)
	public CipherAndKey getCipherTextWithKey(@Named("plaintext") String m){
		HandleKey handleKey = new HandleKey();	
		return handleKey.getCipherTextWithKey(m);
	}
	
	@ApiMethod(name = "generateDecipher", path = "generateDecipher", httpMethod = HttpMethod.GET)
	public Message generateDecipher(@Named("grilleKey") ArrayList<Integer> grilleKey){
		HandleKey handleKey = new HandleKey();
		Message response = new Message();
		response.setData( handleKey.generateDecipher(grilleKey).toString() );
		return response;
	}
	
	@ApiMethod(name = "decipher", path = "decipher", httpMethod = HttpMethod.GET)
	public Message decipher(@Named("grilleKey") ArrayList<Integer> grilleKey, @Named("ciphertext") String ciphertext){
		HandleKey handleKey = new HandleKey();
		Message response = new Message();
		response.setData( handleKey.decipher(grilleKey, ciphertext) );
		return response;
	}
}