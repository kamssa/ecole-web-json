package ci.kossovo.ecole.web.models;

import java.util.List;

public class Reponse<T> {
	private int statut;
	
	private List<String> messages;
	private T body;
	public Reponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reponse(int statut, List<String> messages, T body) {
		super();
		this.statut = statut;
		this.messages = messages;
		this.body = body;
	}
	public int getStatut() {
		return statut;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}

	
}
