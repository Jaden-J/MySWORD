<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	    <title>XML</title>
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
	    <h1 id="Menu">Menu</h1>
	    <ul>
	        <li><a href="#EDI">EDI</a></li>
	        <li><a href="#Standard">Standard</a></li>
	        <li><a href="#Advantages">Advantages</a></li>
	        <li><a href="#Components">Components</a></li>
	        <li><a href="#Example">Example</a></li>
	    </ul>
	    <h1 id="EDI">XML</h1>
	    <p>
	        <span class="keyword">Extensible Markup Language (XML)</span> is a markup language that defines
	        a set of rules for encoding documents in a format that is both human-readable and machine-readable.
	        It is defined in the XML 1.0 Specification produced by the W3C,
	        and several other related specifications, all gratis open standards.
	    </p>
	    <p>
	        <span class="keyword">The design goals of XML emphasize simplicity, generality, and usability over the Internet.</span>
	        It is a textual data format with strong support via Unicode for the languages of the world.
	        Although the design of XML focuses on documents,
	        it is widely used for the representation of arbitrary data structures, for example in web services.
	    </p>
	    <p>
	        Here is a simple XML:
	    </p>
	    <div class="example">
	        &lt;?xml version="1.0" encoding="UTF-8" ?&gt;<br/>
	        &lt;family&gt;<br/>
	        &nbsp;&nbsp;&nbsp;&nbsp;&lt;father&gt;<br/>
	        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;name&gt;father&lt;/name&gt;<br/>
	        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;age&gt;100&lt;/age&gt;<br/>
	        &nbsp;&nbsp;&nbsp;&nbsp;&lt;/father&gt;<br/>
	        &nbsp;&nbsp;&nbsp;&nbsp;&lt;mather&gt;<br/>
	        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;name&gt;mather&lt;/name&gt;<br/>
	        &nbsp;&nbsp;&nbsp;&nbsp;&lt;/mather&gt;<br/>
	        &nbsp;&nbsp;&nbsp;&nbsp;&lt;me/&gt;<br/>
	        &lt;/family&gt;
	    </div>
	    <p>
	        A few things may stand out to you:
	    </p>
	    <ul>
	        <li>
	            The document begins with a processing instruction: <?xml ...?>.
	            This is the XML declaration. While it is not required,
	            its presence explicitly identifies the document as an
	            XML document and indicates the version of XML to which it was authored.
	        </li>
	        <li>
	            There's no document type declaration.
	            XML does not require a document type declaration.
	            However, a document type declaration can be supplied,
	            and some documents will require one in order to be understood unambiguously.
	            Refer to XML schema DTD and XSD, they are defined the XML structure.
	        </li>
	        <li>
	            Empty elements (&lt;applause/&gt; in this example) have a modified syntax.
	            While most elements in a document are wrappers around some content,
	            empty elements are simply markers where something occurs.
	            The trailing "/&gt;" in the modified syntax indicates to
	            a program processing the XML document that the element is empty
	            and no matching end-tag should be sought.
	        </li>
	        <li>
	            XML has softened the distinction between elements
	            which are declared as EMPTY and elements which merely have no content.
	            In XML, it is legal to use the empty-element tag syntax in either case.
	            It's also legal to use a start-tag/end-tag pair for empty elements:
	            &lt;applause&gt;&lt;/applause&gt;. If interoperability is of any concern,
	            it's best to reserve empty-element tag syntax for elements
	            which are declared as EMPTY and to only use the empty-element
	            tag form for those elements.
	        </li>
	    </ul>
	    <p>
	        XML documents are composed of markup and content.
	        There are six kinds of markup that can occur in an XML document:
	        <span class="keyword">Elements, Attributes, Escaping, Comments,
	        Processing Instructions, CDATA, XML declarations</span>.
	        The following sections introduce each of these markup concepts.
	    </p>
	    <hr/>
	    <h1 id="Elements">Elements</h1>
	    <p>
	        Elements are the most common form of markup. Delimited by angle brackets,
	        most elements identify the nature of the content they surround.
	    </p>
	    <p>
	        A logical document component which either begins with a start-tag and ends with a matching end-tag
	        or consists only of an empty-element tag. The characters between the start- and end-tags,
	        if any, are the element's content, and may contain markup, including other elements,
	        which are called child elements.
	    </p>
	    <p>
	        Examples:
	    </p>
	    <div class="example">
	        <table>
	            <tr>
	                <td>Element contains child element</td>
	                <td><b>&lt;mather&gt;&lt;name&gt;mather&lt;/name&gt;&lt;/mather&gt;</b></td>
	            </tr>
	            <tr>
	                <td>Element is not empty</td>
	                <td><b>&lt;name&gt;father&lt;/name&gt;</b></td>
	            </tr>
	            <tr>
	                <td>Element is empty</td>
	                <td><b>&lt;me /&gt;</b></td>
	            </tr>
	        </table>
	    </div>
	    <h1 id="Attributes">Attributes</h1>
	    <p>
	        Attributes are name-value pairs that occur inside start-tags after the element name. For example,
	    </p>
	    <div class="example">
	        &lt;img src="madonna.jpg" alt='Foligno Madonna, by Raphael'/&gt;<br/>
	        &lt;step number="3"&gt;Connect A to B.&lt;/step&gt;
	    </div>
	    <p>
	        is a div element with the attribute class having the value preface. In XML, all attribute values must be quoted.
	   	</p>
	    <h1 id="Escaping">Escaping</h1>
	    <p>
	        In order to introduce markup into a document, some characters have been reserved to identify the start of markup.
	        The left angle bracket, &lt; , for instance, identifies the beginning of an element start- or end-tag.
	        In order to insert these characters into your document as content, there must be an alternative way to represent them.
	        In XML, entities are used to represent these special characters.
	        Entities are also used to refer to often repeated or varying text and to include the content of external files.
	    </p>
	    <p>
	        XML provides escape facilities for including characters which are problematic to include directly. For example:
	    </p>
	    <ul>
	        <li>
	            <span class="keyword">
	                The characters "&lt;" and "&amp;" are key syntax markers and
	                may never appear in content outside a CDATA section.
	            </span>
	        </li>
	        <li>
	            Some character encodings support only a subset of Unicode.
	            For example, it is legal to encode an XML document in ASCII,
	            but ASCII lacks code points for Unicode characters such as "Ã©".
	        </li>
	        <li>
	            It might not be possible to type the character on the author's machine.
	        </li>
	        <li>
	            Some characters have glyphs that cannot be visually distinguished from other characters: examples are
	            <ul>
	                <li>non-breaking space (&amp;#xa0;) " " --> compare space (&amp;#x20;) " "</li>
	                <li>Cyrillic Capital Letter A (&amp;#x410;) "Ð" --> compare Latin Capital Letter A (&amp;#x41;) "A"</li>
	            </ul>
	        </li>
	    </ul>
	    <p>
	        There are five predefined entities:
	    </p>
	    <ul>
	        <li><span class="keyword">&amp;lt;</span> represents "<"</li>
	        <li><span class="keyword">&amp;gt;</span> represents ">"</li>
	        <li><span class="keyword">&amp;amp;</span> represents "&"</li>
	        <li><span class="keyword">&amp;apos;</span> represents '</li>
	        <li><span class="keyword">&amp;quot;</span> represents "</li>
	    </ul>
	    <p>
	        <span class="keyword">All permitted Unicode characters may be represented with a numeric character reference.</span>
	        Consider the Chinese character "ä¸­", whose numeric code in Unicode is hexadecimal 4E2D,
	        or decimal 20,013. A user whose keyboard offers no method for entering this character
	        could still insert it in an XML document encoded either as &amp;#20013; or &amp;#x4e2d;.
	        Similarly, the string "I <3 JÃ¶rg" could be encoded for inclusion in
	        an XML document as "I &amp;lt;3 J&amp;#xF6;rg".
	    </p>
	    <p>
	        <span class="keyword">"&amp;#0;" is not permitted, however, because the null character is one of the control
	        characters excluded from XML, even when using a numeric character reference</span>.
	        An alternative encoding mechanism such as Base64 is needed to represent such characters.
	    </p>
	    <h1 id="Comments">Comments</h1>
	    <p>
	        <span class="keyword">Comments may appear anywhere in a document outside other markup.
	        Comments cannot appear before the XML declaration.
	        Comments start with "<!--" and end with "-->".
	        The string "--" (double-hyphen) is not allowed inside comments;
	        this means comments cannot be nested.</span>
	        The ampersand has no special significance within comments,
	        so entity and character references are not recognized as such,
	        and there is no way to represent characters outside the character set of the document encoding.
	    </p>
	    <p>
	        Comments are not part of the textual content of an XML document.
	        An XML processor is not required to pass them along to an application.
	    </p>
	    <p>
	        An example of a valid comment:
	    </p>
	    <div class="example">"&lt;!-- no need to escape &lt;code&gt; & such in comments --&gt;"</div>
	    <h1 id="Processing">Processing Instructions</h1>
	    <p>
	        Processing instructions (PIs) are an escape hatch to provide information to an application.
	        Like comments, they are not textually part of the XML document,
	        but the XML processor is required to pass them to an application.
	    </p>
	    <p>
	        Processing instructions have the form: &lt;?name pidata?&gt;.
	        The name, called the PI target, identifies the PI to the application.
	        Applications should process only the targets they recognize and ignore all other PIs.
	        Any data that follows the PI target is optional, it is for the application that recognizes the target.
	        The names used in PIs may be declared as notations in order to formally identify them.
	    </p>
	    <p>
	        PI names beginning with xml are reserved for XML standardization.
	    </p>
	    <h1 id="CDATA">CDATA</h1>
	    <p>
	        In a document, a CDATA section instructs the parser to ignore most markup characters.
	    </p>
	    <p>
	        Consider a source code listing in an XML document.
	        It might contain characters that the XML parser would ordinarily recognize as markup (&lt; and &amp;, for example).
	        In order to prevent this, a CDATA section can be used.
	    </p>
	    <div class="example">
	        &lt;![CDATA[ *p = &amp;q;b = (i <= 3);]]&gt;
	    </div>
	    <p>
	        Between the start of the section, <b>"&lt;![CDATA["</b> and the end of the section, <b>"]]&gt;"</b>,
	        all character data is passed directly to the application, without interpretation.
	        Elements, Escaping, Comments, and Processing Instructions are all unrecognized and the characters
	        that comprise them are passed literally to the application.
	        <span class="keyword">The only string that cannot occur in a CDATA section is "]]&gt;".</span>
	    </p>
	    <h1 id="Declaration">XML Declaration</h1>
	    <p>
	        XML documents may begin by declaring some information about themselves, as in the following example:
	    </p>
	    <div class="example">&lt;?xml version="1.0" encoding="UTF-8" ?&gt;</div>
	</body>
</html>