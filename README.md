# janis

Janis is a library for the migration and seeding of Amazon's DynamoDB. It was created as we had issues with [juxt/joplin](https://github.com/juxt/joplin) when trying to use it on an AWS production account.

There are two main objectives:

- First, provide a declarative way (edn files) to represent DynamoDB tables. Janis will take care of doing the necessary calls to setup the table as requested.
- Second, only tables described need to be taken care of, so multiple projects can have their own tables on DynamoDb. This is important as DynamoDB doesn't have partitions beyond AWS regions.

## Installation

If you want to use Janis inside your code you will need to add it to your dependency sequence dependency

    :dependencies [[janis "0.1.0-SNAPSHOT"]]


If you want to use the Leiningen plugin then you will need to use one of this two options:
FIXME: Use this for user-level plugins:

Put `[janis "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your `:user`
profile.

FIXME: Use this for project-level plugins:

Put `[janis "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your project.clj.

## Usage

### Setup

By default, Janis will expect to find the edn files with the table descriptions under a directory called `resources/janis`. It will also use by default the latest version of each table (see on main options for versioning).

The location of the edn files can be changed. All commands will receive an options map. The Leiningen plugin will provide this map based on what is found on project.clj based on 
    
### The main options

These are the basic calls

    (janis/setup)
    (janis/seed)
    (janis/setup setup-opts db)
    (janis/seed seed-opts db)
    

'setup' is the main function to run. `seed` is mostly provided for running on development, but nothing stops you to use it for production purposes.

`setup` will:

- parse the options on the map `setup-opts`, and the `db` used
- read all the files on the specified location on `setup-opts` (The description of the map is below)
- merge all versions of a table up to and including the version indicated on the `setup-opts` map. If no version is indicated it will use `:latest`
- Compare the table(s) map generated with the table(s) on the Dynamodb instance indicated by `db`.
- Based on the comparison, generate a script to modify the table
- Unless otherwise stated on `setup-opts`, will ask for confirmation
- In case the primary key has changed, it will check that all existing records can use the new primary key.
- In case secondary indexes have changed, it will check that all existing records can use the new secondary indexes (types match)
- Will finally run the script to modify the table to conform to what is indicated 

`seed` will:

- parse the options on the map `seed-opts`, and the `db` used
- read all the files on the specified location on `seed-opts` (The description of the map is below)
- check that the seed data conforms to the table specs
- add all the data

### The option maps

There are default versions of both 'setup-opts' and `seed-opts`. If you provide one, it will will merge with the default one, overriding any common key.

The possible keys on `setup-opts` are:

- `:version`. Can be either they keyword `:latest` or a semantic version number. By default is `:latest`
- `:location`. The location of the description files. By default `resources/janis`


The possible keys on `seed-opts` are:

- `:location`. The locaiton of the seed files. By default `resources/janis/seed`
### Leiningen Plugin

FIXME: and add an example usage that actually makes sense:

    $ lein janis

## License

Initial Copyright (C) 2017 Jorge Gueorguiev Garcia

Janis is distributed under the  GNU Affero General Public License v3 (AGPL-3.0).
Please check separate License file for the full text of the license.
