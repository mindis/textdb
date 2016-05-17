package edu.uci.ics.textdb.dataflow.common;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.Analyzer;

import edu.uci.ics.textdb.api.common.Attribute;
import edu.uci.ics.textdb.api.common.FieldType;
import edu.uci.ics.textdb.api.common.IPredicate;
import edu.uci.ics.textdb.api.storage.IDataStore;

/**
 * This class is responsible for calculating regex match. <br>
 * It uses RegexToGramQueryTranslator to translate a regex to a query in lucene. <br>
 * 
 * @author Zuozhi Wang
 * @author Shuying Lai
 * @author sandeepreddy602
 *
 */
public class RegexPredicate implements IPredicate {

	private String regex;
	private List<String> fieldNameList;
	
	private Analyzer analyzer;
	private IDataStore dataStore;	


	public RegexPredicate(String regex, List<Attribute> attributeList, Analyzer analyzer, IDataStore dataStore) {
		this.regex = regex;
		this.analyzer = analyzer;
		this.dataStore = dataStore;
		this.fieldNameList = attributeList.stream()
				.filter(attr -> (attr.getFieldType() == FieldType.TEXT || attr.getFieldType() == FieldType.STRING))
				.map(attr -> attr.getFieldName())
				.collect(Collectors.toList());
	}


	public String getRegex() {
		return regex;
	}
	
	public Analyzer getAnalyzer() {
		return this.analyzer;
	}
	
	public IDataStore getDataStore() {
		return this.dataStore;
	}
	
	public List<String> getFieldNameList() {
		return this.fieldNameList;
	}
	
}
