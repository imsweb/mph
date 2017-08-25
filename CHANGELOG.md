## Multiple Primary and Histology Coding Rules Version History

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

