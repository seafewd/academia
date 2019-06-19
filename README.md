# Academia NG

This is the project root directory of the Student Administration System 'Academia'.
This is a [Maven](http://www.maven.org) project.

------
Run with JDK 8
------


To compile the binaries, type:

>
> `mvn clean package`
>

To generate the project documentation, type:

>
> `mvn clean package site`
>

To run the application within TomEE in order to test it with
a browser, type:

>
> `mvn tomee:run`
>

Then open your browser at <http://localhost:8080/>.

To use one of the profiles defined in the POM, run TomEE via the following Maven command:

>
> mvn tomee:run -P\<Profile ID\>
>

To learn more on the different profiles for the database configurations, see file `./src/site/apt/db_memo.apt`.

Upon pushing the local source files of the project to the GitLab CI/CD server,
build, test, and site generation processes are automatically performed. The
output of the site generation can be found here:
<http://academia-ng.pages.ti.bfh.ch/academia-ng-white>.
