Simple JSON API tasks
===

This project contains a partially completed class and associated unit tests. It's based around data extracted from the http://openexchangerates.org JSON API. It's possible to sign up and use the API directly, but to simplify things, I've downloaded a couple of weeks worth of data into src/main/resources.

The class to complete is 'org.i5y.json.workshop.currency.Rates'.

When complete, the test can be run with maven. Up to that point, I recommend using eclipse which will let you run tests even with the Rates class in its partially completed state.

To set up the project for eclipse, run 'mvn eclipse:eclipse' in the project directory and in eclipse, select 'Import > Existing project into workspace' and select the directory containing this file. 

JSR 353/ JSON API javadoc: http://json-processing-spec.java.net/nonav/releases/1.0/pfd-draft1/javadocs/index.html