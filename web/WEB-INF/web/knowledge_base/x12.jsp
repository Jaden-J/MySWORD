<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	    <title>X12</title>
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
            <li><a href="#ANSIX12">ANSI ASC X12</a></li>
            <li><a href="#X12Standard">X12 Standard</a></li>
            <li><a href="#Leaves">X12 Leaves</a></li>
            <li><a href="#Segment">X12 Segment</a></li>
            <li><a href="#Element">X12 Element</a></li>
            <li><a href="#Composite">X12 Composite</a></li>
            <li><a href="#ElementType">X12 Element Type</a></li>
            <li><a href="#Interchange">Interchange Envelopes</a></li>
            <li><a href="#FunctionGroup">Function Group Envelopes</a></li>
            <li><a href="#TransactionSet">Transaction Set Envelopes</a></li>
            <li><a href="#Loops">Loops in X12</a></li>
        </ul>
	    <h1 id="ANSIX12">ANSI ASC X12</h1>
	    <p>
	        <span class="keyword">ANSI</span> is an abbreviation for the American National Standards Institute that has been coordinating
	        standards in the United States since 1918. ANSI offers an open forum for all concerned to identify needs, create plans to meet those needs,
	        and come to an agreement on the proposed standards.
	    </p>
	    <p>
	        The Institute has a number of committees including the <span class="keyword">ANSI Accredited Standards Committee X12 (ANSI ASC X12)</span>.
	        This committee is a voluntary standards committee that consists of subcommittees representing both private
	        and public sectors in many industries. The subcommittees use a consensus process to propose a new standard
	        or changes to existing standards. These standards enable the electronic exchange of business transactions.<br/><br/>
	        The standard that has been recommended by this committee is known as the ANSI ASC X12  Standard.
	        <u>It is sometimes called the ANSI X12 Standard or simply the X12 Standard</u>.
	    </p>
	    <p>
	    	<span class="keyword">
	            Delimiters are essential in deciphering the contents of an EDI file and getting data out of it.
	            Because the parser needs to know the identity of these character before it can do meaningful parsing.
	            It must know where to find them.
	        </span>
	    </p>
        <ul>
            <li>
                <div class="keyword">Segment Delimiter</div>
                When this delimiter occur, it means Segment end.
            </li>
            <li>
                <div class="keyword">Element Delimiter</div>
                When this delimiter occur, it means Element end.
            </li>
            <li>
                <div class="keyword">Composite Delimiter</div>
                When this delimiter occur, it means Composite end.
            </li>
        </ul>
        <p>
	        Now here are the rules:
	    </p>
        <ul>
            <li>
                <span class="keyword">
                    Delimiters must be unique. Even if you don't use the sub-element,
                    you still have to use a different character than used in the other delimiters.
                </span>
            </li>
            <li>
                <span class="keyword">
                    Delimiters must be single characters.
                </span>
            </li>
            <li>
                <span class="keyword">
                    Delimiters must be printable ASCII characters, excluding the alpha and numbered characters.
                    Also excluding the common punctuation.(having the letter "B" as a delimiter would make a file unreadable.
                </span>
            </li>
            <li>
                <span class="keyword">
                    There are characters that are forbidden like the back tick.
                </span>
            </li>
            <li>
                <span class="keyword">
                    There are characters that are allowed like "*" but that are appear commonly in text fields
                    and descriptions, so they should be avoided.
                </span>
            </li>
        </ul>
	    <h1 id="X12Standard">X12 Standard</h1>
	    <p>
	        You will receive the X12 Standards manual that includes:
	    </p>
        <ul>
            <li>
                <div class="keyword">Transaction Set Tables</div>
                Transaction Sets are commonly used business transactions (PO,Invoice, etc.).
                They are made up of Segments which are made up of Data Elements.
            </li>
            <li>
                <div class="keyword">Segment Directory</div>
                A listing of all possible segments.
            </li>
            <li>
                <div class="keyword">Data Element Dictionary</div>
                A definition for all possible data elements.
            </li>
            <li>
                <div class="keyword">Code Sources</div>
                ANSI ASC X12 allows industry-specific codes. These codes are not maintained by the ANSI ASC X12 committee,
                but by industry organizations. A list of where to obtain these industry codes can be found in the ASC X12 Standards manual.
            </li>
            <li>
                <div class="keyword">Interactive Control Structure</div>
                These are syntax rules at the application level that have been agreed upon by the
                United Nations Economic Commission for Europe (UN/ECE) for
                <span class="keyword">Electronic Data Interchange for Administration, Commerce, and Transport (EDIFACT)</span>
            </li>
        </ul>
	    <h1 id="Leaves">X12 Leaves</h1>
        <img src="../image/knowledge_base/x12/Leaves.png" width="480px" />
        <img src="../image/knowledge_base/x12/Leaves%20Sample.png" width="500px" />
        <p>
	        For every message there are three levels of enveloping:
        </p>
        <ul>
            <li><span class="keyword">Interchange</span></li>
            <li><span class="keyword">Functional Group</span></li>
            <li><span class="keyword">Transaction Set</span></li>
        </ul>
	    <h1 id="Segment">X12 Segment</h1>
	    <img src="../image/knowledge_base/x12/Segment.png" />
	    <p>
	        Every Segment has a Segment ID, it should be the first Element. Segment ID has defined the segment's usage.
        </p>
        <p>
	        e.g. <span class="keyword">PER</span> means <span class="keyword">Administrative Communications Contract</span>.
        </p>
        <p>
	        <span class="keyword">Segments contain:</span>
        </p>
        <ul>
            <li>
                <div class="keyword">Data element separator or delimiter</div>
                Every element has been split by Element Delimiter.
            </li>
            <li>
                <div class="keyword">Variable length data elements</div>
                Data segments contain a collection of data elements in a defined order. Refer to <a href="#Element">X12 Element</a>.
            </li>
            <li>
                <div class="keyword">Segment terminator</div>
                The data segment is terminated using a segment terminator character.The segment terminator (in this example (n/l))
                is for optics only. In ANSI, a non-printable character can be used as a segment terminator.
                Whatever segment terminator character is used, that character cannot be used in the actual content of the data.
                <div class="keyword">See also "Segment Delimiter" in the first option</div>.
            </li>
        </ul>
	    <h1 id="Element">X12 Element</h1>
        <img src="../image/knowledge_base/x12/Element.png" width="400px" />
        <p>
	        In releases prior to October 1993, data segments are defined in diagrams. However, many industry
	        implementation guidelines use the diagram format to define data segment usage. Above image is an
	        example of these diagrams:
        </p>
        <ul style="list-style: decimal">
            <li>
                <div class="keyword">Data Segment Identifier</div>
                It is Segment ID.
            </li>
            <li>
                <div class="keyword">Reference Designator within Segment</div>
                <b>The position according to Segment ID. Count from 1 (we always count the Segment ID as 0).</b>
            </li>
            <li>
                <div class="keyword">Data Dictionary Number</div>
                Every element has its dictionary number, you can find it in X12 dictionary.
            </li>
            <li>
                <div class="keyword">Data Element Separator</div>
                All data elements are separated by a data element delimiter. Refer to delimiter in <a href="#Segment">X12 Segment</a>.
            </li>
            <li>
                <div class="keyword">Data Element Title</div>
                This is the description of this element.
            </li>
            <li>
                <div class="keyword">Data Segment Terminator</div>
                We also call it segment delimiter. Refer to delimiter in <a href="#Segment">X12 Segment</a>.
            </li>
            <li>
                <div class="keyword">Condition Designator (M, O, X) or Semantic Note Designator (Z)</div>
                Data element usage within a segment is
                <ul>
                    <li>
                        <div class="keyword">Mandatory (M)</div>
                        Elements must contain data.
                    </li>
                    <li>
                        <div class="keyword">Optional (O)</div>
                        Elements may or may not contain data depending on the requirements of a particular transmission.
                    </li>
                    <li>
                        <div class="keyword">Relational (X)</div>
                        Elements may or may not contain data depending on the requirements of a particular transmission.<br/>
                        Here is a list of the element ID prefixes and their meaning.
                        <ul>
                            <li>
                                <div class="keyword">P Paired or Multiple</div>
                                If any element specified in the relational condition is present, then all must be present.
                            </li>
                            <li>
                                <div class="keyword">P Paired or Multiple</div>
                                If any element specified in the relational condition is present, then all must be present.
                            </li>
                            <li>
                                <div class="keyword">R Required</div>
                                At least one specified in the relational condition must be present.
                            </li>
                            <li>
                                <div class="keyword">E Exclusion</div>
                                Not more than one can be present.
                            </li>
                            <li>
                                <div class="keyword">C Conditional</div>
                                If the first element specified in the condition is present, then all others must be present.
                            </li>
                            <li>
                                <div class="keyword">L List Conditional</div>
                                If the first element specified is present, then at least one of the remaining elements must be present.
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li>
                <div class="keyword">Data Element Type (N, B, R, AN, ID, DT, TM)</div>
                Refer to <a href="#ElementType">X12 Element Type</a>.
            </li>
            <li>
                <div class="keyword">Data Element Length (Minimum/Maximum)</div>
            </li>
        </ul>
	    <h1 id="Composite">X12 Composite</h1>
	    <p>
	        X12 Composite or Component is just similar with <a href="#Element">X12 Element</a>.
        </p>
	    <h1 id="ElementType">X12 Element Type</h1>
        <ul>
            <li>
                <div class="keyword">Numeric</div>
                Numeric data element types contain only numeric characters and a minus sign if they are negative.
                <b>The digit after "N" defines the number of implied decimal positions</b>. Therefore, N0 indicates
                a numeric value with no decimal position (a whole number); N2 indicates numeric value
                with implied two decimal positions.
                <div><img src="../image/knowledge_base/x12/Numberic.png" /></div>
            </li>
            <li>
                <div class="keyword">String</div>
                String data element types contain alphanumeric characters, including imbedded spaces.
                <div><img src="../image/knowledge_base/x12/String.png" /></div>
            </li>
            <li>
                <div class="keyword">Decimal</div>
                Decimal data element types <b>contain only numeric characters, a decimal point (if needed), and a
                minus sign if negative</b>.
                <div><img src="../image/knowledge_base/x12/Decimal.png" /></div>
            </li>
            <li>
                <div class="keyword">Date</div>
                <b>The date in the format: YYMMDD or CCYYMMDD. The eight-digit date is allowed for version/releases 003072 and later</b>.
                <div><img src="../image/knowledge_base/x12/Date.png" /></div>
            </li>
            <li>
                <div class="keyword">Time</div>
                The time in the format: HHMMSSd...d <b>(24-hour clock)</b>. The length of the data element determines
                the significance of the time specified. The minimum length is four, therefore at least
                HHMM are specified. If the maximum length is six, then up to HHMMSS can be transmitted.
                A longer maximum length allows for decimal portions of seconds to be specified.
            </li>
            <li>
                <div class="keyword">Binary</div>
                A sequence of octets ranging in value from 00000000 to 11111111. This data element type has
                no defined maximum length. The length is specified by the preceding data element.
            </li>
            <li>
                <div class="keyword">ID</div>
                <p>
	                If ANSI ASC X12 maintains the list of ID codes, the code values follow the data element definition
	                in the data element dictionary.
                </p>
                If ANSI ASC X12 does not maintain the ID code list, the source is in <b>the Appendix of the ANSI
                ASC X12 manual</b>. ID codes of this type include:
                <ul>
                    <li><b>Dunn & Bradstreet codes</b></li>
                    <li><b>Medical codes</b></li>
                    <li><b>Airport codes</b></li>
                    <li><b>Telecommunications codes</b></li>
                    <li><b>Petroleum codes</b></li>
                    <li><b>Bureau of Census codes</b></li>
                    <li><b>Government (tax, traffic, armed services) codes</b></li>
                    <li><b>Pharmaceuticals codes</b></li>
                </ul>
                <p class="keyword">
                    ANSI ASC X12 uses a pair of data elements together. The first data element is a qualifier.
                    It defines what the second element (the value) means. Qualifiers are typically ID (code value) fields.
                </p>
                <p>
	                For example, in the 'PER' segment, the pair of data
	                elements PER03 and PER04 determines the method of contacting someone. The qualifier (365)
	                is a code (ID field) with values:
                </p>
                <ul>
                    <li><b>BN</b> Beeper Number</li>
                    <li><b>FX</b> Facsimile</li>
                    <li><b>TE</b> Telephone</li>
                    <li><b>TL</b> Telex</li>
                    <li><b>TM</b> Telemail</li>
                    <li><b>EM</b> Electronic Mail</li>
                    <li><b>CP</b> Cell Phone</li>
                </ul>
                <p>
	                These codes can be used to qualify a particular value to show what kind of communication number
	                is in a particular PER segment:
                </p>
                <img src="../image/knowledge_base/x12/Qualifier%20Sample.png" />
            </li>
        </ul>
	    <h1 id="Interchange">Interchange Envelopes</h1>
	    <p>
	        The outermost level is the Interchange envelope that is defined by ISA and IEA segments. The ISA/IEA data
	        elements start with the letter "I" and are found at the end of the data element dictionary in
	        the Standards manual. <b>The Interchange envelope encloses the data from one sender to one receiver.</b>
	        <b>The ISA is a fixed length segment.This segment is always 106 characters long</b>,
	        counting the first character "I" as 1. This means that if our parser grabs the 106th character
	        it shows the segment delimiter. And the two characters that preceded it are the element and sub-element.
	    </p>
        <ul>
            <li><span class="keyword">Element Delimiter = Character 104</span></li>
            <li><span class="keyword">Composite Delimiter = Character 105</span></li>
            <li><span class="keyword">Segment Delimiter = Character 106</span></li>
        </ul>
        <table>
            <tr>
                <th>Element</th>
                <th>Title</th>
                <th>Condition</th>
                <th>Type</th>
                <th>Min</th>
                <th>Max</th>
                <th>Description</th>
            </tr>
            <tr>
                <td><b>ISA01</b></td>
                <td>Authorization Information Qualifier</td>
                <td>M</td>
                <td>ID</td>
                <td>2</td>
                <td>2</td>
                <td>
                    ISA_01 is a qualifier.The Authorization Information Qualifier can be one of 7 values from 00 to 06.
                    Most of the time it is 00 which means that ISA_02 will not contain meaningful data.
                    This field must be populated with one of these values.
                </td>
            </tr>
            <tr>
                <td><b>ISA02</b></td>
                <td>Authorization Information</td>
                <td>M</td>
                <td>AN</td>
                <td>10</td>
                <td>10</td>
                <td>
                    ISA_02 is for Authorization Information.If the ISA_01 is 00, this will normally be left blank.
                    If ISA_01 is one of the other 6 valid values, this field must be populated.
                    The value here will be used to determine the authorization of the document.
                    I have only seen this used with one trading partner.
                    You might not see this at all.
                </td>
            </tr>
            <tr>
                <td><b>ISA03</b></td>
                <td>Security Information Qualifier</td>
                <td>M</td>
                <td>ID</td>
                <td>2</td>
                <td>2</td>
                <td>
                    ISA_03 is also a Qualifier.It is the Security Information Qualifier and can be one of two values, 00 or 01.
                    Most of the time it is 00 which means that ISA_04 contains no password.
                    This field must be populated with one of these values.
                </td>
            </tr>
            <tr>
                <td><b>ISA04</b></td>
                <td>Security Information</td>
                <td>M</td>
                <td>AN</td>
                <td>10</td>
                <td>10</td>
                <td>
                    ISA_04 is for Security Information.This is also called the password.
                    If the ISA_03 is 01, this must contain a value.
                    Most of the time this will be blank.
                </td>
            </tr>
            <tr>
                <td><b>ISA05</b></td>
                <td>Interchange ID Qualifier</td>
                <td>M</td>
                <td>ID</td>
                <td>2</td>
                <td>2</td>
                <td>
                    ISA_05 is an Interchange ID Qualifier.There are two of these.
                    They qualify or define the Interchange ID field that they precede.
                    These are required to be present.There are 41 valid options,and I won't define them all here.
                    Some common usages are:
                    <ul style="margin: 0px">
                        <li><b>01</b> = DUNS number</li>
                        <li><b>12</b> = Phone number</li>
                        <li><b>14</b> = Duns Number with Suffix</li>
                        <li><b>16</b> = DUNS with 4 character suffix</li>
                        <li><b>ZZ</b> = mutually defined</li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td><b>ISA06</b></td>
                <td>Interchange Sender ID</td>
                <td>M</td>
                <td>AN</td>
                <td>15</td>
                <td>15</td>
                <td>
                    ISA_06 is the Sender's Interchange Identifier.This value will identify the sender to the receiver.
                    The receiver should have this as a unique value.Thus some times it is necessary to
                    have more than one option in case the value you have chosen is already in used by some
                    other Trading Partner that the receiver exchanges documents with.
                </td>
            </tr>
            <tr>
                <td><b>ISA07</b></td>
                <td>Interchange ID Qualifier</td>
                <td>M</td>
                <td>ID</td>
                <td>2</td>
                <td>2</td>
                <td>
                    ISA_07 is an Interchange ID Qualifier.It follows the same rule as ISA_05 but qualifies the
                    receiver instead of the sender.
                </td>
            </tr>
            <tr>
                <td><b>ISA08</b></td>
                <td>Interchange Receiver ID</td>
                <td>M</td>
                <td>AN</td>
                <td>15</td>
                <td>15</td>
                <td>
                    ISA_08 is the Receiver's Interchange Identifier.This values tells the sender's system
                    where the message is going, and tells the receiver's system that this message is for them.
                    The sender will need to have a unique value for each trading partner.
                </td>
            </tr>
            <tr>
                <td><b>ISA09</b></td>
                <td>Interchange Date</td>
                <td>M</td>
                <td>DT</td>
                <td>6</td>
                <td>6</td>
                <td>
                    ISA_09 is the Interchange date.This is the date that the ISA envelope is created.
                    Sometimes this value is derived from the contents of the document,
                    but ideally this should be the date of the interchange envelope.
                </td>
            </tr>
            <tr>
                <td><b>ISA10</b></td>
                <td>Interchange Time</td>
                <td>M</td>
                <td>TM</td>
                <td>4</td>
                <td>4</td>
                <td>
                    ISA_10 is the interchange time.This is the time that the ISA envelope is created.
                    Sometimes this values is derived from the document, or is defaulted to midnight.
                    Ideally this should be the time of the interchange envelope.
                </td>
            </tr>
            <tr>
                <td><b>ISA11</b></td>
                <td>Interchange Control Standards Identifier</td>
                <td>M</td>
                <td>ID</td>
                <td>1</td>
                <td>1</td>
                <td>ISA_11 is the Standard Identifier.This will always be 'U' identifying this as the US EDI standard.</td>
            </tr>
            <tr>
                <td><b>ISA12</b></td>
                <td>Interchange Control Version Number</td>
                <td>M</td>
                <td>ID</td>
                <td>5</td>
                <td>5</td>
                <td>
                    ISA_12 is the Standard Version Number.This relates to the major version of the EDI
                    standard that is used.  This value needs to be accurate so that the receiver's
                    EDI system can expect the right size of fields in the GS segments.
                    This may not look like what it is, as you see this '00401' for a version of '4010'.
                    This only identifies the major version, not distinguishing between 4010, 4011 and 4012.
                </td>
            </tr>
            <tr>
                <td><b>ISA13</b></td>
                <td>Interchange Control Number</td>
                <td>M</td>
                <td>N0</td>
                <td>9</td>
                <td>9</td>
                <td>
                    ISA_13 is the Interchange control number.This is the way that an EDI system identifies the envelope.
                    This control number should not be random, but an incrementing number.
                    It is 9 digits padded with zeroes.The combination of ISA Sender and Receiver Identifiers
                    and the Control number should identify a distinct interchange transmission.
                    When 9 '9′s are reached, the control number should roll back to 1.
                    Nine zeros should be avoided as some systems that may internally trim the zeros
                    will them be presented with a null value.
                </td>
            </tr>
            <tr>
                <td><b>ISA14</b></td>
                <td>Acknowledgment Requested</td>
                <td>M</td>
                <td>ID</td>
                <td>1</td>
                <td>1</td>
                <td>
                    ISA_14 is the Acknowledgment Request.This lets the EDI file make a request for a
                    functional acknowledgment or 997 to be transmitted. This is a binary option of 0
                    meaning "don't send a 997". and 1 meaning "send a 997" In a perfect world any ISA
                    with a 1 in this would get a 997 returned.? In the real world, most EDI systems
                    require some setup to enable the 997, thus it is good to discuss this before sending the request.
                </td>
            </tr>
            <tr>
                <td><b>ISA15</b></td>
                <td>Usage Indicator</td>
                <td>M</td>
                <td>ID</td>
                <td>1</td>
                <td>1</td>
                <td>
                    ISA_15 is the usage indicator.This can be one of three values P is for Production Data,
                    T is for mysword.test.Test Data, and I is for Information Definition.
                    This is important as a EDI system should only process interchanges with the P as production data.
                </td>
            </tr>
            <tr>
                <td><b>ISA16</b></td>
                <td>Component Element Separator</td>
                <td>M</td>
                <td>AN</td>
                <td>1</td>
                <td>1</td>
                <td>ISA_16 are the delimiters.You may remember from the delimiter discussion that these are in a fixed position.</td>
            </tr>
        </table>
	    <p>
	    	Note:
    	</p>
        <ul>
            <li>
                <span class="keyword">Pay attention to the ISA05, ISA06, ISA07 and ISA08.
                These are the sender and receiver fields.</span>
            </li>
            <li>
                <span class="keyword">Increment you control numbers to make sure that you
                EDI files don't get booted as dupes.</span>
            </li>
            <li><span class="keyword">Make sure the ISA and IEA have matching control numbers.</span></li>
        </ul>
	    <h1 id="FunctionGroup">Function Group Envelopes</h1>
	    <p>
	        The second (middle) level of enveloping is the Functional Group Envelope. It shares some properties with the ISA segment.
	        There is a sender and receiver, version Identifier, time stamp and control number.
	        But one thing that the GS has that the ISA does not is a Functional Identifier. That's right, Functional.
	        This means that within the GS envelope are only EDI messages or documents that have the same function.<br/><br/>
	        <b>Unlike the ISA segment, that does not change in any way from standard to standard, the GS may change a bit.</b>
	        e.g. Date and Time in version 004010 is 6/6 and 4/4, but in version 003020 is 8/8 and 4/8.<br/><br/>
        </p>
        <table>
            <caption>V004010 Specification</caption>
            <tr>
                <th>Element</th>
                <th>Dic.#</th>
                <th>Title</th>
                <th>Condition</th>
                <th>Type</th>
                <th>Min</th>
                <th>Max</th>
                <th>Description</th>
            </tr>
            <tr>
                <td><b>GS01</b></td>
                <td>479</td>
                <td>Functional Identifier Code</td>
                <td>M</td>
                <td>ID</td>
                <td>2</td>
                <td>2</td>
                <td>
                    GS01 is the Functional Identifier. This designates the type of messages this envelope contains.
                    If it contains Purchase Orders, the GS_01 is "PO", if it contains Invoices, it is "IN" as so forth.
                </td>
            </tr>
            <tr>
                <td><b>GS02</b></td>
                <td>142</td>
                <td>Application Sender's Code</td>
                <td>M</td>
                <td>AN</td>
                <td>2</td>
                <td>15</td>
                <td>
                    GS02 is the Sender Identifier. This does not have to be the same as the ISA Sender,
                    but it can be. This element has the same restrictions on content,
                    a 15 character alpha-numeric. But unlike the ISA sender, this one is not fixed in size.
                    This means that the delimiters fit around the value without including any white space.
                    In many basic implementations, the value in the ISA sender,
                    and the GS sender are the same value.
                </td>
            </tr>
            <tr>
                <td><b>GS03</b></td>
                <td>124</td>
                <td>APPlication Receiver's Code</td>
                <td>M</td>
                <td>AN</td>
                <td>2</td>
                <td>15</td>
                <td>
                    GS03 is the Receiver Identifier. This does not have to be the same as the ISA receiver,
                    but it can be. This element has the saem restrictions on content, a 15 character alpha-numeric.
                    But unlike the ISA sender, this one is not fixed in size.
                    This means that the delimiters fit around the values without including any white space.
                    In many basic implementations,
                    the values in the ISA receiver and the GS receiver are the same value.
                </td>
            </tr>
            <tr>
                <td><b>GS04</b></td>
                <td>373</td>
                <td>Date</td>
                <td>M</td>
                <td>DT</td>
                <td>6</td>
                <td>6</td>
                <td>
                    GS04 is the Date. It should be the date of the creation of the GS envelope.
                    Many times it is the same date as the ISA date.
                </td>
            </tr>
            <tr>
                <td><b>GS05</b></td>
                <td>337</td>
                <td>Time</td>
                <td>M</td>
                <td>TM</td>
                <td>4</td>
                <td>6</td>
                <td>
                    GS05 is the Time. It should be the time of the creation of the GS envelope.
                    Many times it is the same as the ISA time.
                </td>
            </tr>
            <tr>
                <td><b>GS06</b></td>
                <td>28</td>
                <td>Group Control Number</td>
                <td>M</td>
                <td>N0</td>
                <td>1</td>
                <td>9</td>
                <td>
                    GS06 is the Control Number.? This is a 1 to 9 numeric value.
                    This is a number that needs to be unique inside the ISA envelope only.
                    Some implementations use an incrementing number that increments with
                    the usage like the ISA control number. Others just have a count of the
                    number of GS envelopes inside an ISA.? Both are acceptable.
                    All that is required is that the GS control number be unique among
                    other GS control numbers within the ISA envelope that it is contained in.
                </td>
            </tr>
            <tr>
                <td><b>GS07</b></td>
                <td>455</td>
                <td>Responsible Agency Code</td>
                <td>M</td>
                <td>ID</td>
                <td>1</td>
                <td>2</td>
                <td>
                    GS07 is the Agency Code. Basically for any X12 document that will be "T" or "X".
                    I have only seen "X" indicating that it is really using X12. Even when it is not.
                    Most of the time I ignore this value.
                </td>
            </tr>
            <tr>
                <td><b>GS08</b></td>
                <td>480</td>
                <td>Version / Release / Industry Identifier Code</td>
                <td>M</td>
                <td>AN</td>
                <td>1</td>
                <td>12</td>
                <td>
                    GS08 is a Version identification value. Just like the ISA12,
                    this identifies the standard, but identifies it to the next level.
                    Thus if you are 4010, this will be 004010 and so on.
                </td>
            </tr>
        </table>
	    <p>
            Note:
        </p>
        <ul>
            <li>
                <span class="keyword">Pay attention to the GS02 and GS03, these are the sender and receiver
                and need to be connect to route documents and match with the profile for most systems.</span>
            </li>
            <li>
                <span class="keyword">Make sure the GS01 is the type of document that you are really sending.
                Some systems get cranky when they get an Invoice in a Purchase Order envelope.</span>
            </li>
            <li>
                <span class="keyword">And as always, make sure your GS and GE control numbers match and are
                unique inside the ISA-IEA envelope.</span>
            </li>
        </ul>
	    <h1 id="TransactionSet">Transaction Set Envelopes</h1>
	    <p>
	        The 3rd leave of the EDI envelope is the ST segment.The ST segment contains a Document Identifier,
	        and a control number. The ST and its partner the SE segment define the beginning and ending of the Document.
	        The SE segment contains a counter of segments within the document, and the corresponding control number to the ST.
	        Relatively simple compared to the ISA and the GS.
	    </p>
        <ul>
            <li>
                <div class="keyword">ST01 Document Identifier</div>
                The document Identifier is not encoded. That is to say, for an 850 the ST document ID is "850". For an 810,
                the Document Identifier is "810" and so on. Thus if you open an EDI file, and don't recognize the GS_01,
                skip to the ST_01 and it will tell you what standard you will need to find to read this document.<br/><br/>
                Good form would dictate that the GS_01 and the ST_01 would specify the same type of document.
                The GS would specify it on a functional level, and the ST on the standard level.
                Thus they would not be identical strings but would indeed convey the same message about what
                the document(s) would be. Some EDI translator or processing software will validate this relationship
                and some may not.But in the end consistency is the best policy.<br/><br/>
            </li>
            <li>
                <div class="keyword">ST02 Control Number</div>
                The control number is different for the ST than we saw in both the ISA and GS.
                <b>The ST_02 is a 3 to 9 digit number. This means that it will start with 001 not 000000001.
                Of course you can still use a 000000001 but don't be surprised when you see less than 9
                digits in the ST-SE control number. Whichever you chose, you must do the same for both the ST and SE.</b>
                <br/><br/>
                (Using 000 or 000000000 may be allowed, but is not recommended as any application that trims
                the zeroes will get an empty string and produce an error.Be nice and only use values greater than zero.)
                <br/><br/>
            </li>
            <li>
                <div class="keyword">SE01 Segment Counter</div>
                The SE segment counter is the part of the ST-SE that gives people hand editing an EDI document the most trouble.
                If the counter is inaccurate it indicates that the document is corrupted.
                This should fail in all good translators as processing a corrupt EDI file is saying,
                "Its okay if we get bad data." This counter represents the number of segment between the ST and before the SE.
                <br/><br/>
                And it is just that simple. Remember that you can have more than one GS-GE envelope inside an ISA-IEA,
                and you can have more than one ST-SE inside a GS-GE,
                but you can only have one document of one type in side a ST-SE envelope.
                <br/><br/>
            </li>
        </ul>
	    <p>
            Note:
        </p>
        <ul>
            <li>
                <span class="keyword">
                    A transaction set (i.e., 850, 810, etc.) is a standard for a business document.
                    For example, 850 is used for a purchase order, 810 for an invoice.
                </span>
            </li>
            <li>
                <span class="keyword">
                    Transaction sets all begin with a mandatory ST segment and end with a mandatory SE segment.
                </span>
            </li>
            <li>
                <span class="keyword">
                    Transaction sets may contain a unique, mandatory beginning segment (e.g., BEG in the PO).
                </span>
            </li>
        </ul>
	    <h1 id="Loops">Loops in X12</h1>
	    <p>
	        In EDI there is a construct called a Loop. But this is not like you think of a loop using an while statement.
	        EDI is a file format, not a programming language. <span class="keyword">When we speak of Loops in EDI,
	        what we are really talking about is repeated structures, or segments</span>.
        </p>
        <p>
	        EDI is not a flat file, but it does have repeated lines or segments. Sometimes this repetition
	        comes in groups and sometimes its just a line that get repeated in part of the document.
	        Repeating structured data is fundamental to EDI, but can become difficult to interpret at times.
	        Understanding the types of repeating structures and when and where they may occur can save a lot of time.<br/><br/>
	        There are three kinds of loops/repeated segments;
        </p>
        <ul>
            <li>
                <div class="keyword">Repeating Segment</div>
                <b>A repeating segment is pretty much just what it sounds like, a segment that may occur more than one time.</b>
                A good example of this would be the AMT segment. The AMT is a segment that contains a monetary value.
                The AMT01 declares what type of value it is, and the AMT02 has the amount of money.
                In an EDI document like an invoice, we may need to have many values, subtotals, taxes, base price, and so on.
                Having a repeating segment lets us put all of these values on their own independent segment
                to be interpreted and included as best fits the Trading Partners and the nature of the transaction.<br/><br/>
                Here is an example of an unwrapped N1 group where the N3 is repeated. When segments are repeatable, 
                they are indicated with a "Max Use" value of greater than one in the standards and usage specification.
                In the N1 Group, the N2 and N3 segments may have a max use of 2.<br/>
                <div class="example">
                    N1*AC*ARTHUR JONES*1*9012345918341*11*SE~<br/>
                    N2*GEORGE WASHINGTON~<br/>
                    N3*157 WEST 57TH STREET~<br/>
                    N3*Suite 2001*5th Floor~<br/>
                    N4*CINCINNATI*OH*43017~
                </div>
                <br/>
            </li>
            <li>
                <div class="keyword">Repeating Group</div>
                <b>A repeating group is also what it sounds like. It is a group of segments that work together and get repeated.</b>
                This loop is generally referred to by the primary segment. This segment is required in this group.
                This means that if this group occurs, this segment must also occur.<br/><br/>

                Most Repeating Groups are contextual. This means that they are defined by the segments that surround them,
                and are terminated by the first segment that is not in the group, or starting the group again.
                So a Repeating Group may repeat, but as soon as we move out of the segments in this group,
                we are done repeating it and move on to the next segments or groups defined in the standard.<br/><br/>

                The N1 is a good example. The N1 group contains name, address and contact related information.
                The only required segment is the N1.  Subordinate to the N1 (that contains name and such) is
                the N2 (that contains secondary name or attention type data.) Then comes the N3
                (with the street address) and then the N4 (with the city, state, country, zip).<br/><br/>

                Not all groups have the same letters, like 'N' and the N1 group is no different.
                It also contains the PER segment that contains a persons name, and contact info like phone number or email.
                Any PER that happens under an N1 is for that location.<br/><br/>

                In a document like an order you may want to have the address and contact for the shipper,
                the receiver, a biller, and a payer. There are many possibilities,
                and these are all repeated in this group.Here is an example of an unwrapped N1 group where the that is repeated:
                <br/>
                <div class="example">
                    <div style="background: peru; border: #000000 solid thin; padding: 10px 10px; margin: 10px 10px; width: 450px;">
                        N1*ST*MRS JONES*1*123456789*11*SE~<br/>
                        N2*BEVERLY SIMPSON~<br/>
                        N3*157 WEST 57TH STREET~<br/>
                        N4*CINCINNATI*OH*43017~
                    </div>
                    <div style="background: peru; border: #000000 solid thin; padding: 10px 10px; margin: 10px 10px; width: 450px;">
                        N1*SF*MR JONES*1*9012345918341*11*SE~<br/>
                        N2*MAGGIE MCGILLICUTTY~<br/>
                        N3*1001 MAPLE STREET~<br/>
                        N4*PITTSBURGH*PA*86753~
                    </div>
                </div>
                <br/>
            </li>
            <li>
                <div class="keyword">Repeating Envelop</div>
                <b>A repeating envelope makes reference to EDI Enveloping.
                Basically, the ISA-IEA envelope can contain one or more GS-GE envelopes.
                And each GS-GE can contain more than one ST-SE envelope.</b>
                These levels of enveloping are covered in greater detail on the EDI Enveloping page.
                But it doesn’t have to stop there. In the 3050 850 document there is a loop called LS-LE that contains terms.
                This section happens inside the ST-SE.
                And there is nothing to prevent other sub envelops from happening where there is a need.
                <br/><br/>
                What is fundamental to these types of loops is that they have a designated beginning, and ending.
                They must have both.  Repeating Groups end when a beginning of a new iteration of the loop is found,
                or a segment that is not in the group is found. This can leave some ambiguity if the standard is not followed closely.
                The Repeating Envelope leaves nothing to chance and declares that it is done.
                <br/><br/>
                For example:
                <div class="example">
                    <div style="background: #009999; padding: 10px 10px; margin: 10px 10px; border: solid thin #000000; width: 450px">
                        ISA
                        <div style="margin: 10px 10px; background: pink; border: #000000 solid thin; padding: 10px 10px">
                            GS
                            <div style="margin: 10px 10px; background: peru; border: #000000 solid thin; padding: 10px 10px">
                                ST<br/>
                                ...<br/>
                                SE<br/>
                            </div>
                            <div style="margin: 10px 10px; background: peru; border: #000000 solid thin; padding: 10px 10px">
                                ST<br/>
                                ...<br/>
                                SE<br/>
                            </div>
                            GE
                        </div>
                        <div style="margin: 10px 10px; background: pink; border: #000000 solid thin; padding: 10px 10px">
                            GS
                            <div style="margin: 10px 10px; background: peru; border: #000000 solid thin; padding: 10px 10px">
                                ST<br/>
                                ...<br/>
                                SE<br/>
                            </div>
                            GE
                        </div>
                        IEA
                    </div>
                </div>
            </li>
        </ul>
        <p class="tips">
            You may have been noticed in the examples above or in the specification that we referenced
            that there are required segments in loops that are optional.
            This is not a contradiction and I will explain this here.
            A loop may be optional. If it is, then it may be ommitted entirely from a document
            while still compliant with the standard.  If the loop is included,
            then the required and optional segments within that loop apply.
            And they apply in each instance of the loop.
            (We can’t have an second N1 loop unless we include the required N1 segment.)
        </p>
	    <p>
            Basic Fundamentals to Remember
        </p>
        <ul>
            <li>
                <span class="keyword">
                    Some segments can occur more than once, or can be repeated.
                </span>
            </li>
            <li>
                <span class="keyword">
                    Some groups of segments can and often do occur more than once.
                    These are contextual and are terminated by segments after the group in the standard.
                </span>
            </li>
            <li>
                <span class="keyword">
                    Some segments are encapsulated in beginning and ending segments like enveloping.
                    These segments can repeat, but only in another encapsulation or envelope.
                </span>
            </li>
            <li><span class="keyword">The standard will indicate when repetition is allowed.</span></li>
        </ul>
	</body>
</html>