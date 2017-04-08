package com.edutilos.main;

public class Browser {
   private String header ; 
   private double value;
public Browser(String header, double value) {
	super();
	this.header = header;
	this.value = value;
}
public Browser() {
	super();
}
public String getHeader() {
	return header;
}
public void setHeader(String header) {
	this.header = header;
}
public double getValue() {
	return value;
}
public void setValue(double value) {
	this.value = value;
}
@Override
public String toString() {
	return "Browser [header=" + header + ", value=" + value + "]";
} 
   
}
