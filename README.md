# algorithms
Self training on algorithms

This project just contain a bunch of algorithms serving as a refresher for me. 
There no interrest for one to use this as a lib, as all these algorithms are already included in standard libraries, 
fully debugged, optimized and all. 

As such while you'll find of course unit tests, and a continuous integration build thanks to github automation, 
there no effort to be overly generic. We work on int arrays there and use the natural order comparator on int for simplicity. 
A production like lib would of course work on collections and arbitrary comparators.

## Getting the project and build it
Clone the project, ensuire you have java (14 or greater) and mvn installed and type in your terminal:
mvn clean install

## License

This projet use [MIT license](LICENSE)
