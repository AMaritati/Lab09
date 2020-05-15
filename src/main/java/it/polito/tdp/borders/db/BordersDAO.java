package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public void loadIdMapCountries(Map<Integer,Country> idMap) {
		String sql = "SELECT * FROM country";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(!idMap.containsKey(rs.getInt("CCode"))) {
				Country country = new Country(rs.getString("StateAbb"),rs.getInt("CCode"),rs.getString("StateNme"));
				idMap.put(country.getIdCountry(),country);
				}
			}

			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Border> getCountryPairs(Map<Integer,Country> idMap, int anno) {

		String sql = "SELECT state1no,state1ab,state2no,state2ab,YEAR " + 
				"FROM contiguity " + 
				"WHERE conttype = 1 AND YEAR <= ?";
	
		List<Border> result = new ArrayList<Border>();
	 
		try {
		
			Connection conn = ConnectDB.getConnection();
		
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
		
			ResultSet rs = st.executeQuery();
		
		
			while(rs.next()) {
			
				Country sorgente = idMap.get(rs.getInt("state1no"));
			
				Country destinazione = idMap.get(rs.getInt("state2no"));
			
			
				if(sorgente != null && destinazione != null) {
				
					result.add(new Border(sorgente, destinazione, rs.getInt("YEAR")));
			
				} else{
				
					System.out.println("ERRORE IN GET COUNTRIES");
			
				}

		
			}
		
			conn.close();
	
		}catch (SQLException e) {
		
			e.printStackTrace();
		
			System.out.println("Errore connessione al database");
		
			throw new RuntimeException("Error Connection Database");
	
		}
	
	
		return result;

	}
		
		
}
