package main.java.com.merge;

public class MergePrompt {

	public static final String USER_PROMPT = """
						<USER>

						{
			  "group_id": "19.1",
			  "Object_name": "Supplier",
			  "description": "This group is formed based on the matches applied by Supplier_name",
			  "key_fields": [
			    "SUPPLIER_NAME",
			    "ALTERNATE_NAME",
			    "ALIAS",
			    "TAXPAYER_COUNTRY_NM",
			    "DUNS_NUMBER",
			    "PAY_GROUP_NM",
			    "PRFERD_FUNC_CURR_NM",
			    "SMALL_BUSINESS_FLAG",
			    "DELIVERY_METHOD",
			    "WOMEN_OWNED_FLAG",
			    "INVOICE_CURRENCY_NM",
			    "VAT_CODE",
			    "PAYEE_SERVICE_LEVEL",
			    "SUPPLIER_NUMBER",
			    "TAX_CLASSIFN_CD_NAME",
			    "TAX_REGISTR_NUMBER",
			    "PAYMENT_CURRENCY_NM",
			    "TAXPAYER_ID"
			  ],
			  "field_merge_required_fields": [
			    "SUPPLIER_NAME",
			    "ALTERNATE_NAME",
			    "ALIAS",
			    "TAXPAYER_COUNTRY_NM",
			    "TAX_CLASSIFN_CD_NAME",
			    "DUNS_NUMBER"
			  ],
			  "source_records": [
			    {
			      "record_guid": "1-dw4Umj16DATsQ1",
			      "source_system": "BASE_V14_DEV",
			      "SUPPLIER_NAME": "Walmart Inc.",
			      "ALTERNATE_NAME": "",
			      "ALIAS": "Walmart Inc.",
			      "TAXPAYER_COUNTRY_NM": "USA",
			      "DUNS_NUMBER": "",
			      "PAY_GROUP_NM": "",
			      "PRFERD_FUNC_CURR_NM": "",
			      "SMALL_BUSINESS_FLAG": "",
			      "DELIVERY_METHOD": "",
			      "WOMEN_OWNED_FLAG": "",
			      "INVOICE_CURRENCY_NM": "USD",
			      "VAT_CODE": "VAT54",
			      "PAYEE_SERVICE_LEVEL": "LEVEL2",
			      "SUPPLIER_NUMBER": "",
			      "TAX_CLASSIFN_CD_NAME": "TAXCAT4",
			      "TAX_REGISTR_NUMBER": "",
			      "PAYMENT_CURRENCY_NM": "USD",
			      "TAXPAYER_ID": "25402.0"
			    },
			    {
			      "record_guid": "2-dw4Umj16DATsQ2",
			      "source_system": "BASE_V14_DEV",
			      "SUPPLIER_NAME": "Walmart"
			      "ALTERNATE_NAME": "Walmart",
			      "ALIAS": "Walmart Inc.",
			      "TAXPAYER_COUNTRY_NM": "",
			      "DUNS_NUMBER": "5252999068",
			      "PAY_GROUP_NM": "STANDARD",
			      "PRFERD_FUNC_CURR_NM": "",
			      "SMALL_BUSINESS_FLAG": "",
			      "DELIVERY_METHOD": "POST",
			      "WOMEN_OWNED_FLAG": "Y",
			      "INVOICE_CURRENCY_NM": "USD",
			      "VAT_CODE": "VAT64",
			      "PAYEE_SERVICE_LEVEL": "LEVEL1",
			      "SUPPLIER_NUMBER": "SUP2022",
			      "TAX_CLASSIFN_CD_NAME": "",
			      "TAX_REGISTR_NUMBER": "17-2828833",
			      "PAYMENT_CURRENCY_NM": "USD",
			      "TAXPAYER_ID": "67964.0"
			    },
			    {
			      "record_guid": "3-dw4Umj16DATsQ3",
			      "source_system": "BASE_V14_DEV",
			      "SUPPLIER_NAME": "Walmart India Private Limited",
			      "ALTERNATE_NAME": "",
			      "ALIAS": "Wal Mart Stores",
			      "TAXPAYER_COUNTRY_NM": "India",
			      "DUNS_NUMBER": "9822999068",
			      "PAY_GROUP_NM": "STANDARD",
			      "PRFERD_FUNC_CURR_NM": "",
			      "SMALL_BUSINESS_FLAG": "",
			      "DELIVERY_METHOD": "",
			      "WOMEN_OWNED_FLAG": "",
			      "INVOICE_CURRENCY_NM": "INR",
			      "VAT_CODE": "VAT65",
			      "PAYEE_SERVICE_LEVEL": "LEVEL1",
			      "SUPPLIER_NUMBER": "SUP2023",
			      "TAX_CLASSIFN_CD_NAME": "TAXCAT3",
			      "TAX_REGISTR_NUMBER": "",
			      "PAYMENT_CURRENCY_NM": "INR",
			      "TAXPAYER_ID": "13937.0"
			    }
			  ]
			} </USER>
						""";

	public static final String SYSTEM_PROMPT = """

						 <SYSTEM>
						You are an MDM Match & Survivorship Scoring Engine.
			Input: You will receive exactly one match group per entity type. Each match group contains multiple source records that may represent the same real-world entity.
			Task: To fully evaluate the group and determine match outcome, survivor record, and field-level recommendations suitable for automatic merging in an MDM system.

			Objectives: Perform the following tasks in order
			1.Similarity Scoring
			- Perform pairwise comparison between all records in the group.
			- Calculate similarity scores (0–100) for each scoring dimension.
			- Scores must be numeric, consistent, and explainable.
			2.Pairwise Evaluation
			- Calculate an overall_pair_score using weighted aggregation of all score fields.
			- Ensure overall_pair_score reflects name, identifier, legal, and address alignment.
			3.Group Decision
			- Determine whether the group qualifies for:
			• AUTO_MATCH (eligible for automatic merge)
			• REVIEW (manual data steward review required)
			• AUTO_REJECT (records do not represent the same entity)
			4.Survivor Selection
			- Automatically identify one survivor record using defined survivorship rules.
			- Provide a clear, business-friendly explanation of why this record is the survivor
			5.Field-Level Recommendations
			- Recommend the best value for each field using confidence, completeness, and consistency.

			Pair Score Fields: For every record pair, calculate the following scores (0–100)
			- text_name_score – literal string similarity
			- semantic_name_score – semantic/meaning similarity
			- legal_entity_score – legal entity alignment (type, registration, etc.)
			- address_score – address similarity and normalization match
			- id_score – government/business identifiers match
			- overall_pair_score – weighted final score

			Pair Thresholds:
			- STRONG ≥90
			- LIKELY 80–89
			- POSSIBLE 60–79
			- UNLIKELY 40–59
			- NONE <40

			Group Decisions: Determine the group_decision using the highest and average pair scores
			1.AUTO_MATCH
			- This means the records are extremely similar across names + identifiers + legal + address + IDs.
			2.REVIEW
			- This means partial matches, inconsistencies, or insufficient confidence.
			3.AUTO_REJECT
			- This means records do NOT match at all.

			Group Confidence Mapping:
			- AUTO_MATCH (high): 90–100
			- AUTO_MATCH (good): 70–89
			- REVIEW (weak): 40–69
			- AUTO_REJECT (very_weak): < 40

			Survivor Selection Rules(Strict Priority): Automatically select ONE survivor record using the following priority order
			1. Identifier Strength(Highest Priority)
			- Multiple matching identifiers across records.
			2. Data Completeness
			- Maximum number of populated core fields.
			- Fewer null, empty, or default values.
			3. Data Consistency
			- Align most closely with other records.
			- Have minimal conflicts in name, address, and entity type.
			4. Data Quality Signals
			- Normalized, standardized, and well-formatted values.

			If a tie exists:
			- Prefer records with full legal name (Inc, Corp, Ltd, etc.)
			- Prefer enterprise-standard naming formats

			Survivor Reason(Mandatory): The survivor_reason must
			- Clearly explain why this record is more reliable than others
			- Identify which identifiers or fields influenced the decision most.
			- Explain how it aligns with the majority of records.
			- Explain why other records were not selected.
			- Use plain, business-friendly language suitable for data stewards.

			Field Recommendations: For each recommended field value, include
			- Field Recomendation is required only for field_merge_required_fields
			- survivor_record_guid – survivor record selected in group
			- field_name - chosen field name.
			- value – chosen field value.
			- reason – why this value is preferred.
			- selected_record_guid – identifies the record_guid from which the field value is selected.

			Field selection logic:
			- Override survivor values ONLY if another record has:
			• Higher confidence
			• More complete data
			• More authoritative source

			Output Format: Respond in strict JSON format as follows:

			OUTPUT_JSON_SCHEMA:
			{
			 "group_id": "",
			 "group_decision": "",
			 "group_confidence": 0,
			 "group_reason": "",
			 "survivor_record_id": "",
			 "survivor_confidence": 0,
			 "survivor_reason": "",
			 "survivor_record_guid": "",
			 "pair_scores": [],
			 "field_recommendations": [{
			   "survivor_record_guid": "",
			   "field_name": "",
			   "value": "",
			   "reason": "",
			   "selected_record_guid": ""
			 }]
			}

			Output MUST be valid JSON only.
			<SYSTEM>
						""";

	/*
	 * { "group_id": "string", "entity_type": "string", "source_records": [ {
	 * "record_guid": "string", "name": "string", "legal_entity_type": "string",
	 * "address": { "street": "string", "city": "string", "state": "string",
	 * "postal_code": "string", "country": "string" }, "identifiers": { "tax_id":
	 * "string", "business_id": "string", "government_id": "string" },
	 * "additional_fields": { "field1": "value1", "field2": "value2" } } ] }
	 */

}
