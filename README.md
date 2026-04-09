# PeopleSoft PDF Password Tool (Java)

This project provides a lightweight Java utility to apply password protection to PDF files.

It is designed to be called from PeopleSoft, where native PDF encryption support is limited.

The tool reads PDF files from a source directory, applies a password, and writes the protected files to a target directory.

---

## What this tool does

• Reads PDF files from a source directory  
• Applies a password using Apache PDFBox  
• Writes encrypted PDFs to a target directory  
• Can be executed from command line or invoked from PeopleSoft PeopleCode  

---

## Why this exists

PeopleSoft does not provide a simple or reliable way to password protect PDFs.

Java handles PDF encryption reliably.

This utility keeps PDF security logic outside PeopleSoft and in Java, where it belongs.

---

## Requirements

• Java 8 or later  
• Apache PDFBox  

---
## Downloading the JAR

Prebuilt JAR files are available under GitHub Releases.

Developers do not need to build the project to use it.
Simply download the JAR and reference it from PeopleSoft.

---
## Running from command line

Example

java -jar peoplesoft-pdf-password-tool.jar <sourceDir> <targetDir> <password>

Arguments

• sourceDir directory containing input PDF files  
• targetDir directory where encrypted PDFs will be written  
• password password to apply to PDFs  

Only PDF files are processed.

---

## Using from PeopleSoft

This JAR contains a Java class that is invoked from PeopleSoft using PeopleCode JavaObject.

Typical flow

• PeopleCode creates a JavaObject referencing the class inside the JAR  
• Source directory, target directory, and password are passed as parameters  
• Java handles PDF processing and returns control to PeopleSoft  

This approach keeps PeopleSoft logic simple and delegates PDF handling to Java.

---

## Error handling

### Current behaviour

Based on the current implementation, the tool relies on Java file system and process execution errors and may fail with exceptions or non zero exit codes in cases such as

• Invalid source or target paths  
• File copy failures  
• Failures during PDF encryption  
• Failure to execute the password JAR  

Callers are expected to check the return code and handle any errors appropriately in PeopleCode.

---

### Future enhancements (optional)

If this project is extended further, additional validations and error handling could be added, such as

• Explicit validation of source directory existence  
• Verification that input files are PDFs before processing  
• Validation of password values  
• Clear, structured error messages returned to callers  
• Logging instead of standard output  

These enhancements are intentionally kept out of the current implementation to keep the utility lightweight and flexible.

---

## Security notes

• Passwords are provided at runtime  
• No passwords are stored in code or configuration  
• Callers are responsible for securing runtime values  

---

## License

MIT License.

You are free to use, modify, and distribute this code. This code is shared for reference and educational purposes only.

---

## Disclaimer

This project is provided as is, without warranty of any kind.

Use at your own risk.
