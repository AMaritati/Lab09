package it.polito.tdp.borders.model;

public class Country {
	private String abbreviazione;
	private int idCountry;
	private String nome;
	
	/**
	 * COSTRUTTORE COMPLETO
	 * @param abbreviazione
	 * @param idCountry
	 * @param nome
	 */
	public Country(String abbreviazione, int idCountry, String nome) {
		
		this.abbreviazione = abbreviazione;
		this.idCountry = idCountry;
		this.nome = nome;
	}
	
	/**
	 * COSTRUTTORE LAZY
	 * @param abbreviazione
	 * @param idCountry
	 */
	public Country (String abbreviazione, int idCountry) {
		this.abbreviazione = abbreviazione;
		this.idCountry = idCountry;
	}

	public String getAbbreviazione() {
		return abbreviazione;
	}

	public void setAbbreviazione(String abbreviazione) {
		this.abbreviazione = abbreviazione;
	}

	public int getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(int idCountry) {
		this.idCountry = idCountry;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCountry;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (idCountry != other.idCountry)
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return "("+this.idCountry+") " + this.nome;
	}
	
	
	

}
