<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	    <title>Flat File</title>
	    <link rel="stylesheet" type="text/css" href="../css/main.css" />
        <script type="text/javascript" src="../js/main.js"></script>
        <script type="text/javascript" src="../js/jquery/jquery.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                resetWindowHeight();
            });
        </script>
	</head>
	<body>
	    <h1>Menu</h1>
	        <ul>
	            <li><a href="#FlatFile">Flat File</a></li>
	            <li><a href="#Delimited">Delimited Files</a></li>
	            <li><a href="#Fixed">Fixed Width Files</a></li>
	            <li><a href="#Comparison">Comparison</a></li>
	        </ul>
	    <h1 id="FlatFile">Flat File</h1>
	    <p>
	        <span class="keyword">Files are called “Flat Files” when they contain a single data structure</span>. Generally this structure is the column and row structure like a spreadsheet or table, but a file in binary or encrypted with a single encryption key could also be called a flat file. Files that are not flat; marked up files like XML or HTML, EDI files, other formats like HL7 or SEF files and others. Here I am going to briefly discuss two flat file types; <a href="#Delimited">Delimited Files</a>, and <a href="#Fixed">Fixed Width Files</a>.
	    </p>
	    <h1 id="Delimited">Delimited Files</h1>
	    <p>
	        <span class="keyword">Delimited file is a file where the data is organized in rows and columns.</span> Each row has a set of data, and each column has a type of data. If it sounds like I am describing a spreadsheet, you are right on the money. <span class="keyword">To make the column, each row has the columns separated with a character called a delimiter</span>.
	    </p>
	    <p>
	        See the example below:
	        <img src="../image/knowledge_base/flatfile/Delimited%20Sample.png" width="1000px" />
	        Tables of data and spreadsheets are both similar to a delimited file in the way they organize data.
	        In the delimited file all of the empty space, or white space is removed.
	        What we see here is a classic example of exporting a spreadsheet table as a comma delimited file.
	        In theory, this data can be imported by any other application that can read a delimited file.
	    </p>
	    <p>
	        <span class="keyword">Believe it of not, a space is a character, and takes up space in a file. Back in the day people went out of their way to save space so that files could be send over slow modem connections.</span>
	    </p>
	    <h1 id="Fixed">Fixed Width Files</h1>
	    <p>
	        <span class="keyword">There is another type of file, is is called a Fixed Width or Fixed Position file.</span> It is different from a delimited file in that the data fields are defined by the character position.
	    </p>
	    <p>
	        See the example below:
	        <img src="../image/knowledge_base/flatfile/Fixed%20Sample.png" width="1000px" />
	        <span class="keyword">In a fixed width file, the delimiter characters are eliminated.</span> If the data is formulated such that the data fields are the same size, this format can be more compact than a delimited file. You can see here that we know the size of the Birthdate data, so we eliminate all the spaces between the Bdate and Department fields. If all of the data was formatted for size like this, we could really make this file small, so that it only contains the data.
	    </p>
	    <p>
	        We also eliminate the pesky problem of delimiters found in data. The issue of a comma delimited file containing a field that has a comma in the data. How does the parser know that this comma is not really a delimiter, but is part of the data? Anyway, that problem is eliminated in a fixed width file.
	    </p>
	    <h1 id="Comparison">Comparison</h1>
	    <p>
	        This is not a contest of which format is superior. Both file architectures are useful and both are used commonly enough that you need to be at ease working with both. Delimited files are really easy to work with as long as your data is clean of the delimiter character. Doing quick integration of data common in ETL tasks, delimited files are far more common than Fixed Width. Continuous operations of data integration and importation many times find that Fixed Width or Position files are more reliable for the unattended operation, even ETL if it is unattended.
	    </p>
	    <p>
	        As with many things in integration work, we want to pick the best option. Knowing and working with both fixed and delimited files will help you determine which is the right choice for the task you have before you.
	    </p>
	</body>
</html>