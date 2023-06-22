## Multiple Primary and Histology Coding Rules Version History

**Changes in version 1.29**

- Renamed group IDs and names.
- Updated HematoDB same primary function to handle obsolete codes.

**Changes in version 1.28**

- Updated HematoDB data with latest data from SEER API.

**Changes in version 1.27**

- Renamed all group IDs and names and exposed the names in the output objects.

**Changes in version 1.26**

- Implemented 2023 updates of solid tumor rules.
- Updated hematopoietic histology range.

**Changes in version 1.25**

- Fixed wrong messages for rules.
- Removed unused input TX status.
- Updated 1998 and 2001 hematopoietic rules to return same primary if histologies are the same.
- Fixed a bug in 2004 solid malignant rules.
- Updated HematoDB data with latest data from SEER API.

**Changes in version 1.24**

- Improved message for invalid inputs

**Changes in version 1.23**

- Implemented 2022 updates of solid tumor rules.
- Updated Commons Lang library from version 3.11 to version 3.12.0.
- Updated CSV library from version 5.4 to version 5.5.2.

**Changes in version 1.22**

- Implemented 2021 updates of solid tumor rules.
- Removed lenient mode of computing.
- Updated HematoDB data with latest data from SEER API.
- Fixed a mistake in 2018 head and neck rules.
- Updated Commons Lang library from version 3.9 to version 3.11.
- Updated CSV library from version 5.0 to version 5.4.

**Changes in version 1.21**

- Updated hematopoietic and lymphoid data.

**Changes in version 1.20**

- Fixed a bug in 2018 breast and lung rules.

**Changes in version 1.19**

- Fixed a bug in 2007 malignant brain rules.

**Changes in version 1.18**

- Added site, histology and behavior information to general messages.
- Updated M11 of 2018 breast rule to match the documentation.
- Implemented January 2019 updates to the 2010 hematopoietic rules.

**Changes in version 1.17**

- Changed Hemato data provider to use the latest diagnosis year instead of two years to calculate same primary.
- Updated Commons Lang library from version 3.7 to version 3.9.
- Updated CSV library from version 4.2 to version 5.0.

**Changes in version 1.16**

- Added generic reason messages for 1998 and 2001 Hematopoietic rules.

**Changes in version 1.15**

- Implemented July 2019 updates of multiple primary rules.

**Changes in version 1.14**

- Updated HematoDB data with latest data from SEER API.

**Changes in version 1.13**

- Added 2018 Solid Tumor rules through January 2019 revision.
- Added new getLastDateUpdated to the HematoDB data provider interface.
- Updated HematoDB data with latest data from SEER API.

**Changes in version 1.12**

- Changed Hemato data provider to use two years instead of one to calculate same primary.

**Changes in version 1.11**

- Changed Hemato data provider to use two years instead of one to calculate transforms to and transforms from.

**Changes in version 1.10**

- Changed MphUtils.getAllGroups() to return a map of ID and MphGroup.
- MphGroup names changed from "Year Name" to "Name (Year)".
- Updated HematoDB data with latest data from SEER API.
- Added missing space to message "Unable to apply Rule".
- Standardized and combined "Unable to apply Rule" warning messages.
- Fixed 2007 Malignant Brain Group Charts to contain multiple branches.
- Updated Commons Lang library from version 3.4 to version 3.7.
- Updated CSV library from version 2.4 to version 4.2.
 
**Changes in version 1.9**

- Added a generic rule to return same primary when inputs have same and valid properties.

**Changes in version 1.8**

- Added a new return type for invalid and missing inputs (primary site, histology, behavior and diagnosis year).

**Changes in version 1.7**

- Fixed Date Time Exception for invalid days and months.

**Changes in version 1.6**

- Fixed a null pointer exception in 2007 lung rules.

**Changes in version 1.5**

- Moved lenient mode to be an option on the compute method instead of a global setting of MphUtils.

**Changes in version 1.4**

- Added support to lenient mode of histology matching (8000 is considered as match to any 8nnn histologies); that mode is off by default.

**Changes in version 1.3**

- Improved the way we handle undetermined rules because of invalid or missing data.
- All other histologies are considered as more specific to 8000, NOS.

**Changes in version 1.2**

- Removed 9422 from glial tumors because it is an obsolete.
- Exposed year inclusion of MphGroup.
- The output object has group id and rule step now.

**Changes in version 1.1**

- Removed Joda library dependency, replaced by new Java 8 date framework.
- This library now requires Java 8 at minimum.

**Changes in version 1.0**

- Initial release of the library; extracted from "algorithms" (https://github.com/imsweb/algorithms)

