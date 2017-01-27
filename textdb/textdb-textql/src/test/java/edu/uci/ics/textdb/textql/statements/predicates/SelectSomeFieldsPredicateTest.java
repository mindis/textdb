package edu.uci.ics.textdb.textql.statements.predicates;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.uci.ics.textdb.api.common.Schema;
import edu.uci.ics.textdb.api.exception.TextDBException;
import edu.uci.ics.textdb.common.constants.SchemaConstants;
import edu.uci.ics.textdb.textql.statements.StatementTestUtils;
import edu.uci.ics.textdb.web.request.beans.OperatorBean;
import edu.uci.ics.textdb.web.request.beans.ProjectionBean;

/**
 * This class contains test cases for the SelectSomeFieldsPredicate class.
 * The constructor, getters, setters and the getOperatorBean methods are
 * tested.
 * 
 * @author Flavio Bayer
 *
 */
public class SelectSomeFieldsPredicateTest {
    
    /**
     * Test the class constructor, getters and the setter methods.
     * Call the constructor of the SelectSomeFieldsPredicate, test 
     * if the returned value by the getter is the same as used in 
     * the constructor and then test if the value is changed
     * when the setter method is invoked.
     */
    @Test
    public void testConstructorsGettersSetters(){
        List<String> projectedFields;

        projectedFields = Collections.emptyList();
        assertConstructorGettersSetters(projectedFields);
        
        projectedFields = Arrays.asList("a","b","c","d");
        assertConstructorGettersSetters(projectedFields);
        
        projectedFields = Arrays.asList("field1", "field2", "field0");
        assertConstructorGettersSetters(projectedFields);        

        projectedFields = Arrays.asList(SchemaConstants._ID, SchemaConstants.PAYLOAD, SchemaConstants.SPAN_LIST);
        assertConstructorGettersSetters(projectedFields);
    }
    
    /**
     * Assert the correctness of the Constructor, getter and setter methods.
     * @param projectedFields The list of projected fields to be tested.
     */
    private void assertConstructorGettersSetters(List<String> projectedFields){
        SelectSomeFieldsPredicate selectSomeFieldsPredicate;
        
        // Check constructor
        selectSomeFieldsPredicate = new SelectSomeFieldsPredicate(projectedFields);
        Assert.assertEquals(selectSomeFieldsPredicate.getProjectedFields(), projectedFields);
        
        // Check set projectedFields to null
        selectSomeFieldsPredicate.setProjectedFields(null);
        Assert.assertEquals(selectSomeFieldsPredicate.getProjectedFields(), null);
        
        // Check set projectedFields to the given list of fields
        selectSomeFieldsPredicate.setProjectedFields(projectedFields);
        Assert.assertEquals(selectSomeFieldsPredicate.getProjectedFields(), projectedFields);
    }

    /**
     * Test the getOperatorBean method.
     * Build a SelectSomeFieldsPredicate, invoke the getOperatorBean and check
     * whether a ProjectionBean with the right attributes is returned.
     * An empty list is used as the list of projected fields.
     */
    @Test
    public void testGetOperatorBean00() {
        List<String> projectedFields = Collections.emptyList();
        SelectSomeFieldsPredicate selectSomeFieldsPredicate = new SelectSomeFieldsPredicate(projectedFields);
        
        OperatorBean computedProjectionBean = selectSomeFieldsPredicate.getOperatorBean("xxx");
        OperatorBean expectedProjectionBean = new ProjectionBean("xxx", "Projection", "", null, null);
        
        Assert.assertEquals(expectedProjectionBean, computedProjectionBean);
    }
    
    /**
     * Test the getOperatorBean method.
     * Build a SelectSomeFieldsPredicate, invoke the getOperatorBean and check
     * whether a ProjectionBean with the right attributes is returned.
     * A list with some field names is used as the list of projected fields.
     */
    @Test
    public void testGetOperatorBean01() {
        List<String> projectedFields = Arrays.asList("field0", "field1");
        SelectSomeFieldsPredicate selectSomeFieldsPredicate = new SelectSomeFieldsPredicate(projectedFields);
        
        OperatorBean computedProjectionBean = selectSomeFieldsPredicate.getOperatorBean("zwx");
        OperatorBean expectedProjectionBean = new ProjectionBean("zwx", "Projection", "field0,field1", null, null);

        Assert.assertEquals(expectedProjectionBean, computedProjectionBean);        
    }

    /**
     * Test the getOperatorBean method.
     * Build a SelectSomeFieldsPredicate, invoke the getOperatorBean and check
     * whether a ProjectionBean with the right attributes is returned.
     * A list with some unordered field names is used as the list of projected fields.
     */
    @Test
    public void testGetOperatorBean02() {
        List<String> projectedFields = Arrays.asList("c", "a", "b");
        SelectSomeFieldsPredicate selectSomeFieldsPredicate = new SelectSomeFieldsPredicate(projectedFields);
        
        OperatorBean computedProjectionBean = selectSomeFieldsPredicate.getOperatorBean("op00");
        OperatorBean expectedProjectionBean = new ProjectionBean("op00", "Projection", "c,a,b", null, null);
        
        Assert.assertEquals(expectedProjectionBean, computedProjectionBean);   
    }

    /**
     * Test the generateOutputSchema method.
     * This test use an empty Schema as input and no fields to be projected.
     * The expected output Schema is an empty schema.
     * @throws TextDBException If an exception is thrown while generating
     *  the new Schema.
     */
    @Test
    public void testGenerateOutputSchema00() throws TextDBException {        
        List<String> projectedFields = Collections.emptyList();
        SelectSomeFieldsPredicate selectSomeFieldsPredicate = new SelectSomeFieldsPredicate(projectedFields);
        
        Schema inputSchema = new Schema();
        
        Schema computedOutputSchema = selectSomeFieldsPredicate.generateOutputSchema(inputSchema);
        Schema expectedOutputSchema = new Schema();
        
        Assert.assertEquals(expectedOutputSchema ,computedOutputSchema);
    }

    /**
     * Test the generateOutputSchema method.
     * This test use a Schema with attributes with all values of FieldType
     * and some fields to be projected in the same order which they are in
     * the input schema ("fieldId", "fieldDouble", "fieldString", "fieldList").
     * The expected result is an output schema with the attributes present
     * in the list of fields to be projected.
     * @throws TextDBException If an exception is thrown while generating
     *  the new Schema.
     */
    @Test
    public void testGenerateOutputSchema01() throws TextDBException {
        List<String> projectedFields = Arrays.asList(
                StatementTestUtils.ID_ATTRIBUTE.getFieldName(),
                StatementTestUtils.DOUBLE_ATTRIBUTE.getFieldName(),
                StatementTestUtils.STRING_ATTRIBUTE.getFieldName(),
                StatementTestUtils.LIST_ATTRIBUTE.getFieldName()
            );
        SelectSomeFieldsPredicate selectSomeFieldsPredicate = new SelectSomeFieldsPredicate(projectedFields);
        Schema inputSchema = StatementTestUtils.ALL_FIELD_TYPES_SCHEMA;
        
        Schema computedOutputSchema = selectSomeFieldsPredicate.generateOutputSchema(inputSchema);
        Schema expectedOutputSchema = new Schema(
                StatementTestUtils.ID_ATTRIBUTE,
                StatementTestUtils.DOUBLE_ATTRIBUTE,
                StatementTestUtils.STRING_ATTRIBUTE,
                StatementTestUtils.LIST_ATTRIBUTE
            );
        
        Assert.assertEquals(expectedOutputSchema, computedOutputSchema);
    }
    
    /**
     * Test the generateOutputSchema method.
     * This test use a Schema with attributes with all values of FieldType
     * and some fields to be projected in a different order in which they 
     * are in the input schema("fieldList", "fieldDouble", "fieldString",
     * "fieldId").
     * The expected result is an output schema with the attributes present
     * in the list of fields to be projected.
     * @throws TextDBException If an exception is thrown while generating
     *  the new Schema.
     */
    @Test
    public void testGenerateOutputSchema02() throws TextDBException {
        List<String> projectedFields = Arrays.asList(
                StatementTestUtils.LIST_ATTRIBUTE.getFieldName(),
                StatementTestUtils.DOUBLE_ATTRIBUTE.getFieldName(),
                StatementTestUtils.STRING_ATTRIBUTE.getFieldName(),
                StatementTestUtils.ID_ATTRIBUTE.getFieldName()
            );
        SelectSomeFieldsPredicate selectSomeFieldsPredicate = new SelectSomeFieldsPredicate(projectedFields);
        Schema inputSchema = StatementTestUtils.ALL_FIELD_TYPES_SCHEMA;
        
        Schema computedOutputSchema = selectSomeFieldsPredicate.generateOutputSchema(inputSchema);
        Schema expectedOutputSchema = new Schema(
                StatementTestUtils.ID_ATTRIBUTE,
                StatementTestUtils.DOUBLE_ATTRIBUTE,
                StatementTestUtils.STRING_ATTRIBUTE,
                StatementTestUtils.LIST_ATTRIBUTE
            );
        
        Assert.assertEquals(expectedOutputSchema, computedOutputSchema);
    }

    /**
     * Test the generateOutputSchema method.
     * This test use a Schema with attributes with all values of FieldType
     * and no fields to be projected.
     * The expected output Schema is an empty schema.
     * @throws TextDBException If an exception is thrown while generating
     *  the new Schema.
     */
    @Test
    public void testGenerateOutputSchema03() throws TextDBException {
        List<String> projectedFields = Collections.emptyList();
        SelectSomeFieldsPredicate selectSomeFieldsPredicate = new SelectSomeFieldsPredicate(projectedFields);
        Schema inputSchema = StatementTestUtils.ALL_FIELD_TYPES_SCHEMA;
        
        Schema computedOutputSchema = selectSomeFieldsPredicate.generateOutputSchema(inputSchema);
        Schema expectedOutputSchema = new Schema();
        
        Assert.assertEquals(expectedOutputSchema, computedOutputSchema);
    }

    /**
     * Test the generateOutputSchema method.
     * This test use a Schema with attributes with all values of FieldType
     * and all attributes in the list of fields to be projected.
     * The expected result is an output schema just like the input schema.
     * @throws TextDBException If an exception is thrown while generating
     *  the new Schema.
     */
    @Test
    public void testGenerateOutputSchema04() throws TextDBException {
        List<String> projectedFields = StatementTestUtils.ALL_FIELD_TYPES_SCHEMA.getAttributeNames();
        SelectSomeFieldsPredicate selectSomeFieldsPredicate = new SelectSomeFieldsPredicate(projectedFields);
        
        Schema inputSchema = StatementTestUtils.ALL_FIELD_TYPES_SCHEMA;
        
        Schema computedOutputSchema = selectSomeFieldsPredicate.generateOutputSchema(inputSchema);
        Schema expectedOutputSchema = StatementTestUtils.ALL_FIELD_TYPES_SCHEMA;
        
        Assert.assertEquals(expectedOutputSchema, computedOutputSchema);
    }
    
    /**
     * Test the generateOutputSchema method.
     * This test use an empty Schema as input and field 'x' to be projected.
     * The expected result is a TextDBException being thrown, since attribute 
     * 'x' does not exist in the input schema.
     * @throws TextDBException If an exception is thrown while generating
     *  the new Schema.
     */
    @Test(expected = TextDBException.class)
    public void testGenerateOutputSchema05() throws TextDBException {
        List<String> projectedFields = Arrays.asList("x");
        SelectSomeFieldsPredicate selectSomeFieldsPredicate = new SelectSomeFieldsPredicate(projectedFields); 
        Schema inputSchema = new Schema();
        
        selectSomeFieldsPredicate.generateOutputSchema(inputSchema);
    }
    
    /**
     * Test the generateOutputSchema method.
     * This test use a Schema with attributes with all values of FieldType
     * and a list with valid field names and an invalid field name.
     * The expected result is a TextDBException being thrown, since field
     * 'fieldInvalid' does not exist in the input schema.
     * @throws TextDBException If an exception is thrown while generating
     *  the new Schema.
     */
    @Test(expected = TextDBException.class)
    public void testGenerateOutputSchema06() throws TextDBException {
        List<String> projectedFields = Arrays.asList(
                StatementTestUtils.LIST_ATTRIBUTE.getFieldName(),
                StatementTestUtils.DOUBLE_ATTRIBUTE.getFieldName(),
                "fieldInvalid",
                StatementTestUtils.STRING_ATTRIBUTE.getFieldName(),
                StatementTestUtils.ID_ATTRIBUTE.getFieldName()
            );
        SelectSomeFieldsPredicate selectSomeFieldsPredicate = new SelectSomeFieldsPredicate(projectedFields);
        
        Schema inputSchema = StatementTestUtils.ALL_FIELD_TYPES_SCHEMA;
        
        selectSomeFieldsPredicate.generateOutputSchema(inputSchema);
    }
    
    /**
     * Test the generateOutputSchema method.
     * This test use a Schema with attributes with all values of FieldType
     * and a list with valid field names, ignoring duplicate names.
     * The expected output Schema is an output schema with multiples
     * attributes including the duplicates attributes.
     * @throws TextDBException If an exception is thrown while generating
     *  the new Schema.
     */
    @Test
    public void testGenerateOutputSchema07() throws TextDBException {
        List<String> projectedFields = Arrays.asList(
                StatementTestUtils.LIST_ATTRIBUTE.getFieldName(),
                StatementTestUtils.DOUBLE_ATTRIBUTE.getFieldName(),
                StatementTestUtils.LIST_ATTRIBUTE.getFieldName(),
                StatementTestUtils.STRING_ATTRIBUTE.getFieldName(),
                StatementTestUtils.ID_ATTRIBUTE.getFieldName()
            );
        SelectSomeFieldsPredicate selectSomeFieldsPredicate = new SelectSomeFieldsPredicate(projectedFields);
        
        Schema inputSchema = StatementTestUtils.ALL_FIELD_TYPES_SCHEMA;
        
        Schema computedOutputSchema = selectSomeFieldsPredicate.generateOutputSchema(inputSchema);
        Schema expectedOutputSchema = new Schema(
                StatementTestUtils.ID_ATTRIBUTE,
                StatementTestUtils.DOUBLE_ATTRIBUTE,
                StatementTestUtils.STRING_ATTRIBUTE,
                StatementTestUtils.LIST_ATTRIBUTE
            );
        
        Assert.assertEquals(expectedOutputSchema, computedOutputSchema);
    }

    /**
     * Test the generateOutputSchema method to check whether the name of
     * the projected fields are case-sensitive. 
     * This test use a Schema with all the attributes in SchemaConstants
     * and project some fields with valid name but with different
     * capitalization.
     * The expected output Schema is an output schema with the projected
     * fields (the capitalization of the name of the field should be the
     * same as the original Schema).
     * @throws TextDBException If an exception is thrown while generating
     *  the new Schema.
     */
    @Test
    public void testGenerateOutputSchema08() throws TextDBException {
        List<String> projectedFields = Arrays.asList(
                StatementTestUtils.LIST_ATTRIBUTE.getFieldName().toLowerCase(),
                StatementTestUtils.DOUBLE_ATTRIBUTE.getFieldName().toUpperCase(),
                StatementTestUtils.STRING_ATTRIBUTE.getFieldName().toUpperCase(),
                StatementTestUtils.ID_ATTRIBUTE.getFieldName().toLowerCase()
            );
        SelectSomeFieldsPredicate selectSomeFieldsPredicate = new SelectSomeFieldsPredicate(projectedFields);
        
        Schema inputSchema = StatementTestUtils.ALL_FIELD_TYPES_SCHEMA;
        
        Schema computedOutputSchema = selectSomeFieldsPredicate.generateOutputSchema(inputSchema);
        Schema expectedOutputSchema = new Schema(
                StatementTestUtils.ID_ATTRIBUTE,
                StatementTestUtils.DOUBLE_ATTRIBUTE,
                StatementTestUtils.STRING_ATTRIBUTE,
                StatementTestUtils.LIST_ATTRIBUTE
            );
        
        Assert.assertEquals(expectedOutputSchema, computedOutputSchema);
    }
    
}
