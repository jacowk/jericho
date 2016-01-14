/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.jericho.util.conversion;

import za.co.jericho.common.search.SearchType;

/**
 *
 * @author Jaco Koekemoer
 */
public interface StringConvertor {
    
    public String convertForDatabaseSearchEquals(String data);
    
    public String convertForDatabaseSearchStartsWith(String data);
    
    public String convertForDatabaseSearchEndsWith(String data);
    
    public String convertForDatabaseSearchContains(String data);
    
    public String concatenateStrings(String... strings);
    
    public String convertForDatabaseSearch(String data, SearchType searchType);
    
    public String convertForDatabaseSearchWithNullIfEmpty(String data, SearchType searchType);
    
    public String convertForDatabaseSearch(Long data, SearchType searchType);
    
    public String convertNullToEmpty(String data);
    
    public String convertCamelCaseToTitleCase(String data);
    
    public String convertTitleCaseToCamelCase(String data);
    
}
