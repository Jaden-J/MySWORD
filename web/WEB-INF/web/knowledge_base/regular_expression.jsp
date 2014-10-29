<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	    <title>Regular Expression</title>
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
        <li><a href="#Introduction">Introduction</a></li>
        <li><a href="#Syntax">Syntax</a></li>
        <li><a href="#New">Build a Regular Expression</a></li>
	</ul>
	<h1 id="Introduction">Introduction</h1>
    <p>
        Unless you have previously used regular expressions, the term may be unfamiliar to you. However, you have undoubtedly used some regular expression concepts outside the area of scripting.
    </p>
    <p>
        For example, you most likely use the ? and * wildcard characters to find files on your hard disk. The '?' wildcard character matches a single character in a file name, while the * wildcard character matches zero or more characters. A pattern such as data?.dat would find the following files:
    </p>
    <ul>
        <li>data1.dat</li>
        <li>data2.dat</li>
        <li>datax.dat</li>
        <li>dataN.dat</li>
    </ul>
    <p>
    	Using the * character instead of the ? character expands the number of found files. data*.dat matches all of the following:
    </p>
    <ul>
        <li>data.dat</li>
        <li>data1.dat</li>
        <li>data2.dat</li>
        <li>data12.dat</li>
        <li>datax.dat</li>
        <li>dataXYZ.dat</li>
    </ul>
    <p>
    	While this method of searching is useful, it is also limited. The ability of the ? and * wildcard characters introduces the concept behind regular expressions, but regular expressions are more powerful and flexible.
    </p>
	<h1 id="Syntax">Syntax</h1>
	<p>
	    A regular expression is a pattern of text that consists of ordinary characters (for example, letters a through z) and special characters, known as metacharacters. The pattern describes one or more strings to match when searching text.
	</p>
	<p>
		Examples:
	</p>
	<table cellspacing="0">
	    <tr>
	        <th>Expression</th>
	        <th>Matches</th>
	    </tr>
	    <tr>
	        <td>/^\s*$/</td>
	        <td>Match a blank line.</td>
	    </tr>
	    <tr>
	        <td>/\d{2}-\d{5}/</td>
	        <td>Validate an ID number consisting of 2 digits, a hyphen, and an additional 5 digits.</td>
	    </tr>
	    <tr>
	        <td>/&lt;\s*(\S+)(\s[^&gt;]*)?&gt;[\s\S]*&lt;\s*\/\1\s*&gt;/</td>
	        <td>Match an HTML tag.</td>
	    </tr>
	</table>
	<p>
		The following table contains the complete list of metacharacters and their behavior in the context of regular expressions:
	</p>
	<table cellspacing="0">
		<tr>
		    <th>Character</th>
		    <th>Expression</th>
		</tr>
		<tr>
		    <td>\</td>
		    <td>
		        Marks the next character as a special character, a literal, a backreference, or an octal escape. For example,
		        'n' matches the character "n". '\n' matches a newline character. The sequence '\\' matches "\" and "\(" matches "(".
		    </td>
		</tr>
		<tr>
		    <td>^</td>
		    <td>
		        Matches the position at the beginning of the input string. If the <b>RegExp</b> object's <b>Multiline</b> property is set,
		        ^ also matches the position following '\n' or '\r'.
		    </td>
		</tr>
		<tr>
		    <td>$</td>
		    <td>
		        Matches the position at the end of the input string. If the <b>RegExp</b> object's <b>Multiline</b> property is set,
		        $ also matches the position preceding '\n' or '\r'.
		    </td>
		</tr>
		<tr>
		    <td>*</td>
		    <td>
		        Matches the preceding character or subexpression zero or more times. For example,
		        zo* matches "z" and "zoo". * is equivalent to {0,}.
		    </td>
		</tr>
		<tr>
		    <td>+</td>
		    <td>
		        Matches the preceding character or subexpression one or more times. For example,
		        'zo+' matches "zo" and "zoo", but not "z". + is equivalent to {1,}.
		    </td>
		</tr>
		<tr>
		    <td>?</td>
		    <td>
		        Matches the preceding character or subexpression zero or one time. For example,
		        "do(es)?" matches the "do" in "do" or "does". ? is equivalent to {0,1}
		    </td>
		</tr>
		<tr>
		    <td>{n}</td>
		    <td>
		        n is a nonnegative integer. Matches exactly n times. For example, 'o{2}'
		        does not match the 'o' in "Bob," but matches the two o's in "food".
		    </td>
		</tr>
		<tr>
		    <td>(n,)</td>
		    <td>
		        n is a nonnegative integer. Matches at least n times. For example, 'o{2,}' does not match the "o" in "Bob"
		        and matches all the o's in "foooood". 'o{1,}' is equivalent to 'o+'. 'o{0,}' is equivalent to 'o*'.
		    </td>
		</tr>
		<tr>
		    <td>{n,m}</td>
		    <td>
		        M and n are nonnegative integers, where n &lt;= m. Matches at least n and at most m times. For example,
		        "o{1,3}" matches the first three o's in "fooooood". 'o{0,1}' is equivalent to 'o?'.
		        Note that you cannot put a space between the comma and the numbers.
		    </td>
		</tr>
		<tr>
		    <td>?</td>
		    <td>
		        When this character immediately follows any of the other quantifiers (*, +, ?, {n}, {n,}, {n,m}),
		        the matching pattern is non-greedy. A non-greedy pattern matches as little of the searched string as possible,
		        whereas the default greedy pattern matches as much of the searched string as possible. For example, in the string "oooo",
		        'o+?' matches a single "o", while 'o+' matches all 'o's.
		    </td>
		</tr>
		<tr>
		    <td>.</td>
		    <td>
		        Matches any single character except "\n". To match any character including the '\n', use a pattern such as '[\s\S]'.
		    </td>
		</tr>
		<tr>
		    <td>{pattern}</td>
		    <td>
		        A subexpression that matches pattern and captures the match. The captured match can be retrieved from the resulting
		        Matches collection using the <b>$0â¦$9</b> properties. To match parentheses characters ( ), use '\(' or '\)'.
		    </td>
		</tr>
		<tr>
		    <td>(?:pattern)</td>
		    <td>
		        A subexpression that matches pattern but does not capture the match, that is,
		        it is a non-capturing match that is not stored for possible later use.
		        This is useful for combining parts of a pattern with the "or" character (|).
		        For example, 'industr(?:y|ies) is a more economical expression than 'industry|industries'.
		    </td>
		</tr>
		<tr>
		    <td>(?=pattern)</td>
		    <td>
		        A subexpression that performs a positive lookahead search,
		        which matches the string at any point where a string matching pattern begins.
		        This is a non-capturing match, that is, the match is not captured for possible later use.
		        For example 'Windows (?=95|98|NT|2000)' matches "Windows" in "Windows 2000" but not "Windows" in "Windows 3.1".
		        Lookaheads do not consume characters, that is, after a match occurs,
		        the search for the next match begins immediately following the last match,
		        not after the characters that comprised the lookahead.
		    </td>
		</tr>
		<tr>
		    <td>(?!pattern)</td>
		    <td>
		        A subexpression that performs a negative lookahead search,
		        which matches the search string at any point where a string not matching pattern begins.
		        This is a non-capturing match, that is, the match is not captured for possible later use.
		        For example 'Windows (?!95|98|NT|2000)' matches "Windows" in "Windows 3.1" but does not match "Windows" in "Windows 2000".
		        Lookaheads do not consume characters, that is, after a match occurs,
		        the search for the next match begins immediately following the last match,
		        not after the characters that comprised the lookahead.
		    </td>
		</tr>
		<tr>
		    <td>x|y</td>
		    <td>
		        Matches either x or y. For example, 'z|food' matches "z" or "food". '(z|f)ood' matches "zood" or "food".
		    </td>
		</tr>
		<tr>
		    <td>[xyz]</td>
		    <td>
		        A character set. Matches any one of the enclosed characters. For example, '[abc]' matches the 'a' in "plain".
		    </td>
		</tr>
		<tr>
		    <td>[^xyz]</td>
		    <td>
		        A negative character set. Matches any character not enclosed. For example, '[^abc]' matches the 'p' in "plain".
		    </td>
		</tr>
		<tr>
		    <td>[a-z]</td>
		    <td>
		        A range of characters. Matches any character in the specified range. For example,
		        '[a-z]' matches any lowercase alphabetic character in the range 'a' through 'z'.
		    </td>
		</tr>
		<tr>
		    <td>[^a-z]</td>
		    <td>
		        A negative range characters. Matches any character not in the specified range. For example,
		        '[^a-z]' matches any character not in the range 'a' through 'z'.
		    </td>
		</tr>
		<tr>
		    <td>\b</td>
		    <td>
		        Matches a word boundary, that is, the position between a word and a space.
		        For example, 'er\b' matches the 'er' in "never" but not the 'er' in "verb".
		    </td>
		</tr>
		<tr>
		    <td>\B</td>
		    <td>
		        Matches a nonword boundary. 'er\B' matches the 'er' in "verb" but not the 'er' in "never".
		    </td>
		</tr>
		<tr>
		    <td>\cx</td>
		    <td>
		        Matches the control character indicated by x. For example, \cM matches a Control-M or carriage return character.
		        The value of x must be in the range of A-Z or a-z. If not, c is assumed to be a literal 'c' character.
		    </td>
		</tr>
		<tr>
		    <td>\d</td>
		    <td>
		        Matches a digit character. Equivalent to [0-9].
		    </td>
		</tr>
		<tr>
		    <td>\D</td>
		    <td>
		        Matches a nondigit character. Equivalent to [^0-9].
		    </td>
		</tr>
		<tr>
		    <td>\f</td>
		    <td>
		        Matches a form-feed character. Equivalent to \x0c and \cL.
		    </td>
		</tr>
		<tr>
		    <td>\n</td>
		    <td>
		        Matches a newline character. Equivalent to \x0a and \cJ.
		    </td>
		</tr>
		<tr>
		    <td>\r</td>
		    <td>
		        Matches a carriage return character. Equivalent to \x0d and \cM.
		    </td>
		</tr>
		<tr>
		    <td>\s</td>
		    <td>
		        Matches any white space character including space, tab, form-feed, and so on. Equivalent to [ \f\n\r\t\v].
		    </td>
		</tr>
		<tr>
		    <td>\S</td>
		    <td>
		        Matches any non-white space character. Equivalent to [^ \f\n\r\t\v].
		    </td>
		</tr>
		<tr>
		    <td>\t</td>
		    <td>
		        Matches a tab character. Equivalent to \x09 and \cI.
		    </td>
		</tr>
		<tr>
		    <td>\v</td>
		    <td>
		        Matches a vertical tab character. Equivalent to \x0b and \cK.
		    </td>
		</tr>
		<tr>
		    <td>\w</td>
		    <td>
		        Matches any word character including underscore. Equivalent to '[A-Za-z0-9_]'.
		    </td>
		</tr>
		<tr>
		    <td>\W</td>
		    <td>
		        Matches any nonword character. Equivalent to '[^A-Za-z0-9_]'.
		    </td>
		</tr>
		<tr>
		    <td>\xn</td>
		    <td>
		        Matches n, where n is a hexadecimal escape value. Hexadecimal escape values must be exactly two digits long. For example,
		        '\x41' matches "A". '\x041' is equivalent to '\x04' & "1". Allows ASCII codes to be used in regular expressions.
		    </td>
		</tr>
		<tr>
		    <td>\num</td>
		    <td>
		        Matches num, where num is a positive integer. A reference back to captured matches.
		        For example, '(.)\1' matches two consecutive identical characters.
		    </td>
		</tr>
		<tr>
		    <td>\n</td>
		    <td>
		        Identifies either an octal escape value or a backreference. If \n is preceded by at least n captured subexpressions,
		        n is a backreference. Otherwise, n is an octal escape value if n is an octal digit (0-7).
		    </td>
		</tr>
		<tr>
		    <td>\nm</td>
		    <td>
		        Identifies either an octal escape value or a backreference. If \nm is preceded by at least nm captured subexpressions,
		        nm is a backreference. If \nm is preceded by at least n captures, n is a backreference followed by literal m.
		        If neither of the preceding conditions exists, \nm matches octal escape value nm when n and m are octal digits (0-7).
		    </td>
		</tr>
		<tr>
		    <td>\nm!</td>
		    <td>
		        Matches octal escape value nml when n is an octal digit (0-3) and m and l are octal digits (0-7).
		    </td>
		</tr>
		<tr>
		    <td>\un</td>
		    <td>
		        Matches n, where n is a Unicode character expressed as four hexadecimal digits.
		        For example, \u00A9 matches the copyright symbol (Â©).
		    </td>
		</tr>
	</table>
	<h1 id="New">Build a Regular Expression</h1>
	    <p>
	        The construction of regular expressions is analogous to the construction of arithmetic expressions.
	        That is, a variety of metacharacters and operators combine small expressions to create large expressions.
	    </p>
	    <p>
	        You construct a regular expression by putting the various components of the expression pattern between a pair of delimiters.
	        For JScript, the delimiters are forward slash (/) characters, there maybe flags, such as 'g'/'ig'/'m'. For example:
	    </p>
	    <div class="example">
	        <table cellspacing="0">
	            <tr>
	                <th>Expression</th>
	                <th>mysword.test.Test Data</th>
	                <th>Result</th>
	            </tr>
	            <tr>
	                <td>/DB Schenker/g</td>
	                <td>DB Schenker is DB Schenker.</td>
	                <td><span class="result">DB Schenker</span> is <span class="result">DB Schenker</span>.</td>
	            </tr>
	        </table>
	    </div>
	    <p>
	        How to use: <span class="keyword">\</span>
	    </p>
	    <div class="example">
	        <table cellspacing="0">
	            <tr>
	                <th>Expression</th>
	                <th>mysword.test.Test Data</th>
	                <th>Result</th>
	            </tr>
	            <tr>
	                <td>/0\.1/g</td>
	                <td>0.1 + 0.1 = 0.2</td>
	                <td><span class="result">0.1</span> + <span class="result">0.1</span> = 0.2</td>
	            </tr>
	        </table>
	    </div>
	    <p>
	        How to use quantifiers: <span class="keyword">* + ? {n} {n,} {n,m}</span>
	    </p>
	    <div class="example">
	        <table cellspacing="0">
	            <tr>
	                <th>Codes</th>
	                <th>Expression</th>
	                <th>mysword.test.Test Data</th>
	                <th>Result</th>
	            </tr>
	            <tr>
	                <td>*</td>
	                <td>/ca*A/g</td>
	                <td>aAbcaABaaacACadDaa</td>
	                <td>aAb<span class="result">caA</span>Baaa<span class="result">cA</span>CadDaa</td>
	            </tr>
	            <tr>
	                <td>+</td>
	                <td>/ca+A/g</td>
	                <td>aAbcaABaaacACadDaa</td>
	                <td>aAb<span class="result">caA</span>BaaacACadDaa</td>
	            </tr>
	            <tr>
	                <td>?</td>
	                <td>/ca?A/g</td>
	                <td>aAbcaAcaaABaaacACadDaa</td>
	                <td>aAb<span class="result">caA</span>BaaacACadDaa</td>
	            </tr>
	            <tr>
	                <td>{n}</td>
	                <td>/a{2}/g</td>
	                <td>aAbBaaacCadDaa</td>
	                <td>aAbB<span class="result">aa</span>acCadD<span class="result">aa</span></td>
	            </tr>
	            <tr>
	                <td>{n,}</td>
	                <td>/a{2,}/g</td>
	                <td>aAbBaaacCadDaa</td>
	                <td>aAbB<span class="result">aaa</span>cCadD<span class="result">aa</span></td>
	            </tr>
	            <tr>
	                <td>{n,m}</td>
	                <td>/a{2,3}/g</td>
	                <td>aAbBaaacCadDaaaa</td>
	                <td>aAbB<span class="result">aaa</span>cCadD<span class="result">aaa</span>a</td>
	            </tr>
	        </table>
	    </div>
	    <p>
	        How to use bracket codes: <span class="keyword">{} [] [^] (?:|) (?=|) (?!|)</span>
	    </p>
	    <div class="example">
	        <table cellspacing="0">
	            <tr>
	                <th>Codes</th>
	                <th>Expression</th>
	                <th>mysword.test.Test Data</th>
	                <th>Result</th>
	            </tr>
	            <tr>
	                <td>{pattern}</td>
	                <td>TBD</td>
	                <td>TBD</td>
	                <td>TBD</td>
	            </tr>
	            <tr>
	                <td>[]</td>
	                <td>/[abc]/g</td>
	                <td>DB Schenker Logistic and DB Schenker and DB Schenker Rail</td>
	                <td>DB S<span class="result">c</span>henker Logisti<span class="result">c</span> <span class="result">a</span>nd DB S<span class="result">c</span>henker and DB
	                    S<span class="result">c</span>henker R<span class="result">a</span>il</td>
	            </tr>
	            <tr>
	                <td>[]</td>
	                <td>/[a-z]/g</td>
	                <td>DB Schenker Logistic and DB Schenker and DB Schenker Rail</td>
	                <td>DB S<span class="result">chenker</span> L<span class="result">ogistic</span> <span class="result">and</span> DB S<span class="result">chenker</span>
	                    <span class="result">and</span> DB S<span class="result">chenker</span> R<span class="result">ail</span></td>
	            </tr>
	            <tr>
	                <td>[^]</td>
	                <td>/[^abc]/g</td>
	                <td>DB Schenker Logistic and DB Schenker and DB Schenker Rail</td>
	                <td><span class="result">DB S</span>c<span class="result">henker Logisti</span>c<span class="result"> </span>a<span class="result">nd DB S</span>c
	                    <span class="result">henker </span>a<span class="result">nd DB S</span>c<span class="result">henker R</span>a<span class="result">il</span></td>
	            </tr>
	            <tr>
	                <td>(?:pattern)</td>
	                <td>/DB Schenker (?:Logistic|Rail)/g</td>
	                <td>DB Schenker Logistic and DB Schenker and DB Schenker Rail</td>
	                <td><span class="result">DB Schenker Logistic</span> and DB Schenker and <span class="result">DB Schenker Rail</span></td>
	            </tr>
	            <tr>
	                <td>(?=pattern)</td>
	                <td>/DB Schenker(?=Logistic|Rail)/g</td>
	                <td>DB Schenker Logistic and DB Schenker and DB Schenker Rail</td>
	                <td><span class="result">DB Schenker </span>Logistic and DB Schenker and <span class="result">DB Schenker </span>Rail</td>
	            </tr>
	            <tr>
	                <td>(?!pattern)</td>
	                <td>/DB Schenker (?!Logistic|Rail)/g</td>
	                <td>DB Schenker Logistic and DB Schenker and DB Schenker Rail</td>
	                <td>DB Schenker Logistic and <span class="result">DB Schenker </span>and DB Schenker Rail</td>
	            </tr>
	        </table>
	    </div>
	    <p>
	        How to use anchors: <span class="keyword">^ $</span>
	    </p>
	    <div class="example">
	        <table cellspacing="0">
	            <tr>
	                <th>Codes</th>
	                <th>Expression</th>
	                <th>mysword.test.Test Data</th>
	                <th>Result</th>
	            </tr>
	            <tr>
	                <td>^</td>
	                <td>/^A/g</td>
	                <td>AbcaACadDaA</td>
	                <td><span class="result">A</span>bcaACadDaA</td>
	            </tr>
	            <tr>
	                <td>$</td>
	                <td>/A$/g</td>
	                <td>AbcacACadDaA</td>
	                <td>AbcacACadDa<span class="result">A</span></td>
	            </tr>
	        </table>
	    </div>
	</body>
</html>