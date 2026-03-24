In-Document ChatBot – PDF Parser Module

    ------Overview----------

        This project is a custom-built PDF parser designed for an In-Document Chatbot system.

        Instead of just extracting raw text, this parser:

        Converts PDF into structured blocks
        Identifies headings & paragraphs
        Extracts keywords
        Builds relationships between similar content

        This makes it easier for:

        Chunking
        Search
        AI-based question answering




    -----API Endpoint-------
                Upload & Parse PDF

                POST /api/pdf/parse

                Request (Postman)
                Method: POST
                Body → form-data
                Key: file
                Type: File
                Value: Upload your PDF
                📤 Response Format
                [
                {
                    "id": 1,
                    "text": "Java is a programming language...",
                    "type": "paragraph",
                    "section": "INTRODUCTION",
                    "page": 1,
                    "keywords": ["java", "programming", "language"],
                    "relatedIds": [3, 5]
                }
                ]


    ------------How It Works------------
                Step 1: Upload PDF
                User sends PDF via API
                Step 2: Extract Text
                Using PDFBox
                Page-by-page extraction
                Step 3: Clean Data
                Remove noise (empty lines, page numbers)
                Fix broken words (learn- ing → learning)
                Step 4: Structure Detection
                Detect headings (ALL CAPS)
                Build paragraphs
                Step 5: Keyword Extraction
                Remove stopwords
                Keep meaningful words
                Step 6: Relationship Building
                Uses similarity (Jaccard)
                Groups related blocks
                Uses Disjoint Set (Union-Find)
                🔗 Relation Logic
                Blocks are connected if:

                similarity > 0.4


    -----------Future Improvements------------
    1.Make parser for table,images also.
    2.Make them parsing the text file also at this time they parse pdf only.



   ------------ Tech Stack--------------------
                Java
                Spring Boot
                Apache PDFBox
