/* 
 * The MIT License
 * 
 * Copyright (c) 2010 Bruno P. Kinoshita <http://www.kinoshita.eti.br>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.plugins.testlink.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.eti.kinoshita.testlinkjavaapi.model.Attachment;
import br.eti.kinoshita.testlinkjavaapi.model.CustomField;
import br.eti.kinoshita.testlinkjavaapi.model.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestCaseStep;

/**
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 2.0
 */
public class TestCaseWrapper implements Serializable {

	private static final long serialVersionUID = -1071107752030147906L;

	/**
	 * A list of custom field and status, used to allow the user to use a comma
	 * separated list of custom fields. A result seeker is responsible for
	 * seeking the test results and, for each one found, add the custom field
	 * value and the status.
	 */
	private final Map<String, ExecutionStatus> customFieldAndStatus;

	/**
	 * List of attachments.
	 */
	private List<Attachment> attachments;

	/**
	 * Execution notes.
	 */
	private StringBuilder notes;

	/**
	 * Platform.
	 */
	private String platform = null;

	/**
	 * Wrapped Automated Test Case.
	 */
	private TestCase testCase;

	public TestCaseWrapper() {
		this(new TestCase());
	}
	
	/**
	 * @param testCase
	 *            wrapped automated test case.
	 */
	public TestCaseWrapper(TestCase testCase) {
		this.testCase = testCase;
		this.notes = new StringBuilder();
		this.attachments = new LinkedList<Attachment>();
		this.customFieldAndStatus = new HashMap<String, ExecutionStatus>();
	}

	/**
	 * Add a custom field name and its execution status.
	 * 
	 * @param customField
	 *            custom field name
	 * @param executionStatus
	 *            execution status
	 */
	public void addCustomFieldAndStatus(String customField,
			ExecutionStatus executionStatus) {
		this.customFieldAndStatus.put(customField, executionStatus);
	}

	/**
	 * @return custom field name and execution status
	 */
	public Map<String, ExecutionStatus> getCustomFieldAndStatus() {
		return customFieldAndStatus;
	}

	/**
	 * Adds an attachment to this test case. Use it with caution, as it may case
	 * memory issues if you store many Attachments in memory. The content is
	 * saved as Base64 in memory.
	 * 
	 * @param attachment
	 */
	public void addAttachment(Attachment attachment) {
		this.attachments.add(attachment);
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public String getNotes() {
		return notes.toString();
	}

	public void appendNotes(String notes) {
		this.notes.append(notes);
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public ExecutionStatus getExecutionStatus() {
		ExecutionStatus status = ExecutionStatus.NOT_RUN;
		String[] customFieldsNames = new String[this.testCase.getCustomFields().size()];
		int i = 0;
		for(CustomField cf : this.testCase.getCustomFields()) {
			customFieldsNames[i] = cf.getName();
			i++;
		}
		if (customFieldAndStatus.size() > 0
				&& customFieldAndStatus.size() == customFieldsNames.length) {
			status = ExecutionStatus.PASSED;
			for (ExecutionStatus reportedStatus : customFieldAndStatus.values()) {
				if (reportedStatus == ExecutionStatus.FAILED
						|| reportedStatus == ExecutionStatus.BLOCKED) {
					status = reportedStatus;
					break;
				}
			}
		}
		return status;
	}

	public Integer getId() {
		return this.testCase.getId();
	}
	
	public void setId(Integer id) {
		this.testCase.setId(id);
	}

	public String getName() {

		return this.testCase.getName();
	}

	public void setCustomFields(List<CustomField> customFields) {

		this.testCase.setCustomFields(customFields);
	}
	
	public List<CustomField> getCustomFields() {
		return this.testCase.getCustomFields();
	}

	public void setExecutionStatus(ExecutionStatus executionStatus) {

		this.testCase.setExecutionStatus(executionStatus);
	}

	public void setName(String name) {

		this.testCase.setName(name);
	}

	public Integer getInternalId() {
		return testCase.getInternalId();
	}
	
	public void setInternalId(Integer internalId) {
		this.testCase.setInternalId(internalId);
	}
	
	public Integer getExecutionOrder() {
		return this.testCase.getExecutionOrder();
	}
	
	public void setExecutionOrder(Integer executionOrder) {
		this.testCase.setExecutionOrder(executionOrder);
	}
	
	public Integer getTestSuiteId() {
		return this.testCase.getTestSuiteId();
	}
	
	public void setTestSuiteId(Integer testSuiteId) {
		this.testCase.setTestSuiteId(testSuiteId);
	}
	
	public Integer getTestProjectId() {
		return this.testCase.getTestProjectId();
	}
	
	public void setTestProjectId(Integer testProjectId) {
		this.testCase.setTestProjectId(testProjectId);
	}
	
	public String getAuthorLogin() {
		return this.testCase.getAuthorLogin();
	}
	
	public void setAuthorLogin(String authorLogin) {
		this.testCase.setAuthorLogin(authorLogin);
	}
	
	public String getSummary() {
		return this.testCase.getSummary();
	}
	
	public void setSummary(String summary) {
		this.testCase.setSummary(summary);
	}
	
	public List<TestCaseStep> getSteps() {
		return this.testCase.getSteps();
	}
	
	public void setSteps(List<TestCaseStep> steps) {
		this.testCase.setSteps(steps);
	}

	public Integer getVersion() {
		return testCase.getVersion();
	}

}
