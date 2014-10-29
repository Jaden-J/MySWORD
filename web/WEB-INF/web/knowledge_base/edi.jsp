<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title>EDI</title>
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
            <li><a href="#EDI">EDI</a></li>
            <li><a href="#Standard">Standard</a></li>
            <li><a href="#Advantages">Advantages</a></li>
            <li><a href="#Components">Components</a></li>
            <li><a href="#Example">Example</a></li>
        </ul>
        <h1 id="EDI">EDI</h1>
        <img src="../image/knowledge_base/edi/EDI.png" width="600px" />
        <p>
       		<span class="keyword">Electronic data interchange (EDI)</span> is a method for transferring data between different computer systems or computer networks.It is commonly used by big companies for e-commerce purposes, such as sending orders to warehouses or tracking their order.It is more than mere e-mail; for instance, organizations might replace bills of lading and even cheques with appropriate EDI messages.It also refers specifically to a family of standards.
		</p>
		<p>
	        EDI provides a technical basis for commercial "conversations" between two entities, either internal or external.EDI constitutes the entire electronic data interchange paradigm, including the transmission, message flow, document format, and software used to interpret the documents. EDI standards describe the rigorous format of electronic documents.
	    </p>
	    <p>
	        The EDI standards were designed by the implementers, initially in the Automotive industry,to be independent of communication and software technologies.EDI can be transmitted using any methodology agreed to by the sender and recipient.This includes a variety of technologies, including modem (asynchronous and synchronous), FTP, e-mail, HTTP, AS1, AS2, etc.EDI is widely used by the airline industry, banking industry, credit card industry, and auto industry etc.The current push in the EDI world comes from companies who wish to trade with each other electronically--buyersand their suppliers--hence the term "trading partners."
		</p>
	        Famous EDI Types:
	        <ul>
	            <li><a class="keyword link" href="EDIFACT">EDIFACT</a></li>
	            <li><a class="keyword link" href="X12">ANSI X.12</a></li>
	            <li><a class="keyword link" href="XML">XML</a></li>
	            <li><a class="keyword link" href="FlatFile">Manually Defined Flat File</a></li>
	            <li><a class="keyword link" href="Rosettanet">ROSETTANET</a></li>
	            <li><a class="keyword link" href="IDOC">IDOC</a></li>
	        </ul>
    	<h1 id="Standard">Standard</h1>
        <ul>
            <li><span class="keyword">Define the vocabulary, the syntax as well as the structure of electronic documents.</span></li>
            <li><span class="keyword">Include an extensive list of codes </span></li>
            <li><span class="keyword">Developed in collaboration with interested parties and approved by recognized organization.</span></li>
            <li><span class="keyword">Periodically updated.</span></li>
        </ul>
    	<h1 id="Advantages">Advantages</h1>
        <ul>
            <li><span class="keyword">Manage huge volumes of transactions.</span></li>
            <li>
                <span class="keyword">Eliminates data entry errors and improves accuracy.</span>
                <div>Such as shipping and billing errors, because EDI eliminates the need to rekey documents on the destination side.</div>
            </li>
            <li>
                <span class="keyword">Less operating cost.</span>
                <div>
                    Save a company money by providing an alternative to, or replacing,
                    information flows that require a great deal of human interaction and materials such as paper documents, meetings, faxes, etc.
                    Even when paper documents are maintained in parallel with EDI exchange,
                    e.g. printed shipping manifests, electronic exchange and the use of data from that exchange reduces the handling costs of sorting,
                    distributing, organizing, and searching paper documents.
                    EDI and similar technologies allow a company to take advantage of the benefits of storing and manipulating data electronically
                    without the cost of manual entry.
                </div>
            </li>
            <li>
                <span class="keyword">Eliminates delays.</span>
                <div>
                    Speed in which the trading partner receives and incorporates the information into
                    their system thus greatly reducing cycle times. For this reason,
                    EDI can be an important component of just-in-time production systems.
                </div>
            </li>
        </ul>
        <div>
            According to the 2008 Aberdeen report "A Comparison of Supplier Enablement around the World",
            only 34% of purchase orders are transmitted electronically in North America. In EMEA,
            36% of orders are transmitted electronically and in APAC, 41% of orders are transmitted electronically.
            They also report that the average paper requisition to order costs a company $37.45 in North America,
            $42.90 in EMEA and $23.90 in APAC. With an EDI requisition to order costs are reduced to $23.83 in North America,
            $34.05 in EMEA and $14.78 in APAC.
        </div>
    	<h1 id="Components">Components</h1>
        <ul>
            <li>
                <span class="keyword">Trade Agreement</span>
                <div>
                    A legally binding trade agreement between a company and its trading partner.
                </div>
            </li>
            <li>
                <span class="keyword">Standard Document Format</span>
                <div>
                    The standard agreed upon format for the document to be electronically transmitted.
                </div>
            </li>
            <li>
                <span class="keyword">Business Application software</span>
                <div>
                    The first step in the EDI process is creating a document. ie. An invoice in a software application.
                    The software application then sends the document to an EDI translator Software used to convert the
                    document your application's format into the agreed upon standard format.
                    For optimum performance the translation software should be on the same platform as your business application.
                </div>
            </li>
            <li>
                <span class="keyword">Communications Software</span>
                <div>
                    A programming tool that enables you to write communications protocols, or a separate application.
                    It can be a module to the translator or a separate software application.
                </div>
            </li>
            <li>
                <span class="keyword">Modem</span>
                <div>
                    A hardware device used to transmit electronic information between computer systems.
                </div>
            </li>
            <li>
                <span class="keyword">VAN</span>
                <div>
                    Stands for Value Added Network.A network to which you can connect to transmit data from one computer
                    systems to another. One network can act as a gateway to another.
                </div>
            </li>
            <li>
                <span class="keyword">Point-to-Point</span>
                <div>
                    EDI standards are written such that trading partners could connect directly to each other.
                    For example, an automotive manufacturer might maintain a modem-pool that all of its hundreds
                    suppliers are required to dial into to perform EDI. However, if a supplier does business with several manufacturers,
                    it may need to acquire a different modem (or VPN device, etc.) and different software for each one.
                </div>
            </li>
        </ul>
	    <h1 id="Example">Example</h1>
	    <img src="../image/knowledge_base/edi/Example.png" />
	</body>
</html>