# Model Catalog - Migration Files Creator (MCMFC) #

## Description ##

This tool is used to create migration files for the Model Catalog. It is a tool that takes 3 JSON files from MetaMl as input and generates the migration files for the Model Catalog. The migration files are used to update the Model Catalog database schema. There are 16 init migration files that are used to create the init schema. This tool uses this files but it doesn't modify them.

This java tool is connected with a corresponding python code that is used to create the json files from MetaMl. The python code is located in model-catalog-py directory.

## Usage ##
To use the tool, run the following command:

1. Update MetaMl version
* We create new migration files when metaml has a new version with updated data. To update the version of MetaMl, you need to update the version in the following files:
  * model-catalog-py/setup/requirements.txt
  
2. Create Conda environment to run python code (model-catalog-py) to take data from MetaMl and create json files
```bash
conda create -n model-catalog-py python=3.8
```

3. Activate the environment
```bash
conda activate model-catalog-py
```

4. Install the required packages
```bash
pip install -r model-catalog-migration-file-creator/model-catalog-py/setup/requirements.txt
```    

5. Compile the java code
```bash
mvn clean package
```

6. Run the model catalog migration files creator (Java code)

* From the root directory of the model-catalog project run the following command:
```bash
java -jar model-catalog-migration-file-creator/target/model-catalog-migration-file-creator.jar
```

7. The migration file will be created in the "<b>sql_scripts</b>" directory with an automatically generated name <next_migration_number>-metaml_automatic_tool_update.sql

## Formatting ##

This application is configured to use the Google Java Style Guide for formatting.

* Run formatting using the following command:
  ```
  mvn com.coveo:fmt-maven-plugin:format
  ```

* To check if the code is formatted correctly, run the following command:
  ```
  mvn com.coveo:fmt-maven-plugin:check
  ```
  
