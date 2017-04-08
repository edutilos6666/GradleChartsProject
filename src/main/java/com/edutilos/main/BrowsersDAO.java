package com.edutilos.main;

import java.util.List;

public interface BrowsersDAO {
	void save(Browser browser); 
   List<Browser> findAll(); 
}
