<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>EDIFACT</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css" />
        <script type="text/javascript" src="../js/main.js"></script>
        <script type="text/javascript" src="../js/jquery/jquery.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                resetWindowHeight()
            });
        </script>
	</head>
	<body>
		<h1>Menu</h1>
		<ul>
			<li>
				<a href="#Introduction">Introduction</a>
			</li>
			<li>
				<a href="#Structure">Structure</a>
			</li>
		</ul>
		<h1 id="Introduction">Introduction</h1>
		<p>
			<span class="keyword">United Nations/Electronic Data Interchange For Administration, Commerce and Transport (UN/EDIFACT)</span> is the international EDI standard developed under the United Nations.
		</p>
		<p>
			In 1987, following the convergence of the UN and US/ANSI syntax proposals, the UN/EDIFACT Syntax Rules were approved as the ISO standard ISO 9735 by the International Organization for Standardization.
		</p>
		The EDIFACT standard provides:
		<ul>
			<li>
				A set of syntax rules to structure data.
			</li>
			<li>
				An interactive exchange protocol (I-EDI).
			</li>
			<li>
				Standard messages which allow multi-country and multi-industry exchange.
			</li>
		</ul>
		<h1 id="Structure">Structure</h1>
		<p>
			EDIFACT has a hierarchical structure where the top level is referred to as an interchange, and lower levels contain multiple messages which consist of segments, which in turn consist of composites. The final iteration is an element which is derived from the United Nations Trade Data Element Directory (UNTDED); these are normalised throughout the EDIFACT standard.
		</p>
		A group or segment can be mandatory (M) or conditional (C) and can be specified to repeat. For example :
		<ul>
			<li>C99 indicates between 0 and 99 repetitions of a segment or group</li>
			<li>M99 signifies between 1 and 99 repetitions of a segment or group</li>
		</ul>
		A group, like a message, is a sequence of segments or groups. The first segment or group beneath a group must be mandatory, and the group should be made conditional if the logic of the situation demands it.
		<pre class="example">
    Service String Advice     UNA  Optional
    +----- Interchange Header        UNB  Mandatory
    | +---   Functional Group Header   UNG  Conditional
    | | +-     Message Header            UNH  Mandatory
    | | |        User Data Segments             As required
    | | +-     Message Trailer           UNT  Mandatory
    | +---   Functional Group Trailer  UNE  Conditional
    +----- Interchange Trailer       UNZ  Mandatory</pre>
		For example:
		<pre class="example">
    UNA:+.? '
    UNB+IATB:1+6XPPC+LHPPC+940101:0950+1'
    UNH+1+PAORES:93:1:IA'
    MSG+1:45'
    IFT+3+XYZCOMPANY AVAILABILITY'
    ERC+A7V:1:AMD'
    IFT+3+NO MORE FLIGHTS'
    ODI'
    TVL+240493:1000::1220+FRA+JFK+DL+400+C'
    PDI++C:3+Y::3+F::1'
    APD+74C:0:::6++++++6X'
    TVL+240493:1740::2030+JFK+MIA+DL+081+C'
    PDI++C:4'
    APD+EM2:0:1630::6+++++++DA'
    UNT+13+1'
    UNZ+1+1'</pre>
		<p>
			<span class="keyword">UNA</span> a specify separator characters used in the transmission.
		</p>
		The UNA segment is optional. If present, it specifies the special characters that are to be used to interpret the remainder of the message. There are six characters following UNA in this order:
		<ol>
			<li>
				component data element separator (: in this sample)
			</li>
			<li>
				data element separator (+ in this sample)
			</li>
			<li>
				decimal notification (. in this sample)
			</li>
			<li>
				release character (? in this sample)
			</li>
			<li>
				reserved, must be a space
			</li>
			<li>
				segment terminator (' in this sample)
			</li>
		</ol>
		The special characters in the sample UNA segment above are also the default values.
		<ul>
			<li>
				<span class="keyword">UNB</span> a interchange header, (syntax, sender, receiver, time, control number…)
			</li>
			<li>
				<span class="keyword">UNG,UNE</span> a conditional, only used when transmission carries different groups of messages of different types
			</li>
			<li>
				<span class="keyword">UNH</span> a message counter, message identifier (type, version, release, agency).<br/>
				UNH+1+PAORES:93:1:IA'- This is the header segment which is required at the start of every message. This code specifies that the message name and version is PAORES 93 revision 1 and it was defined by the organisation IA (IATA).
			</li>
			<li>
				<span class="keyword">UNT</span> a number of segments, message counter.<br/>
				UNT+13+1' - This is the tail segment. It indicated that the message sent contains 13 segments.
			</li>
			<li>
				<span class="keyword">UNZ</span> a interchange trailer, (number of messages, control number)
			</li>
		</ul>
	</body>
</html>