package za.co.jericho.util.conversion;

import za.co.jericho.common.search.SearchType;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 *
 * @author Jaco Koekemoer
 */
public class StringDataConvertor implements StringConvertor {
    
    @Override
    public String convertForDatabaseSearchEquals(String data) {
        StringValidator stringValidator = new StringDataValidator();
        if (!stringValidator.isNullOrEmpty(data)) {
            return data;
        }
        else {
            return "%%";
        }
    }
    
    @Override
    public String convertForDatabaseSearchStartsWith(String data) {
        StringValidator stringValidator = new StringDataValidator();
        if (!stringValidator.isNullOrEmpty(data)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(data);
            stringBuilder.append("%");
            return stringBuilder.toString();
        }
        else {
            return "%%";
        }
    }
    
    @Override
    public String convertForDatabaseSearchEndsWith(String data) {
        StringValidator stringValidator = new StringDataValidator();
        if (!stringValidator.isNullOrEmpty(data)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("%");
            stringBuilder.append(data);
            return stringBuilder.toString();
        }
        else {
            return "%%";
        }
    }
    
    @Override
    public String convertForDatabaseSearchContains(String data) {
        StringValidator stringValidator = new StringDataValidator();
        if (!stringValidator.isNullOrEmpty(data)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("%");
            stringBuilder.append(data);
            stringBuilder.append("%");
            return stringBuilder.toString();
        }
        else {
            return "%%";
        }
    }
    
    @Override
    public String concatenateStrings(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String value: strings) {
            stringBuilder.append(value);
        }
        return stringBuilder.toString();
    }
    
    @Override
    public String convertForDatabaseSearch(String data, SearchType searchType) {
        if (searchType == null) {
            return convertForDatabaseSearchContains(data); /* Default search type */
        }
        if (searchType == SearchType.CONTAINS) {
            return convertForDatabaseSearchContains(data);
        }
        if (searchType == SearchType.ENDS_WITH) {
            return convertForDatabaseSearchEndsWith(data);
        }
        if (searchType == SearchType.EQUALS) {
            return convertForDatabaseSearchEquals(data);
        }
        if (searchType == SearchType.STARTS_WITH) {
            return convertForDatabaseSearchStartsWith(data);
        }
        return "%%";
    }
    
    @Override
    public String convertForDatabaseSearchWithNullIfEmpty(String data, SearchType searchType) {
        StringValidator stringValidator = new StringDataValidator();
        if (stringValidator.isNullOrEmpty(data)) {
            return null;
        }
        if (searchType == null) {
            return convertForDatabaseSearchContains(data); /* Default search type */
        }
        if (searchType == SearchType.CONTAINS) {
            return convertForDatabaseSearchContains(data);
        }
        if (searchType == SearchType.ENDS_WITH) {
            return convertForDatabaseSearchEndsWith(data);
        }
        if (searchType == SearchType.EQUALS) {
            return convertForDatabaseSearchEquals(data);
        }
        if (searchType == SearchType.STARTS_WITH) {
            return convertForDatabaseSearchStartsWith(data);
        }
        return "%%";
    }
    
    @Override
    public String convertForDatabaseSearch(Long data, SearchType searchType) {
        if (data != null) {
            return convertForDatabaseSearch(data.toString(), searchType);
        }
        return "%%";
    }
    
    @Override
    public String convertNullToEmpty(String data) {
        if (data == null || data.equals("null")) {
            return "";
        }
        return data;
    }
    
    @Override
    public String convertCamelCaseToTitleCase(String data) {
        if (data == null || data.length() == 0) {
            return data;
        }
        StringBuilder titleCase = new StringBuilder();
        titleCase.append(Character.toString(data.charAt(0)).toUpperCase());
        for (int i = 1; i < data.length(); i++) {
            if (Character.isUpperCase(data.charAt(i))) {
                titleCase.append(" ");
                titleCase.append(data.charAt(i));
            }
            else {
                titleCase.append(data.charAt(i));
            }
        }
        return titleCase.toString();
    }
    
    @Override
    public String convertTitleCaseToCamelCase(String data) {
        if (data == null || data.length() == 0) {
            return data;
        }
        StringBuilder camelCase = new StringBuilder();
        camelCase.append(Character.toString(data.charAt(0)).toLowerCase());
        for (int i = 1; i < data.length(); i++) {
            if (data.charAt(i) != ' ') {
                camelCase.append(data.charAt(i));
            }
        }
        return camelCase.toString();
    }

}
