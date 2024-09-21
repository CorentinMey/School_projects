## Program Objective:

Analysis of a protein structure in Protein Data Bank (PDB) format either by opening a file or retrieving data through the PDB website. Various analyses will be performed on this data and stored in result files.

## Features:

    Retrieve the content of a PDB file either by querying the PDB website using the PDB code (URL example: http://files.rcsb.org/view/1CRN.pdb) or by opening a PDB file stored on your hard drive.
    Retrieve important information about the protein (description, length of the protein, etc.), the experimental method used, along with the associated resolution if available.
    Build the sequence in FASTA format for this protein and offer to save or display it.
    Perform an analysis of the protein’s amino acid composition and compare this result with the average frequency of amino acids in a protein (e.g., based on SwissProt).
    Calculate the hydrophobicity profile of this protein.
    Detect the possible presence of disulfide bonds, display the list of disulfide bridges, and/or free cysteines.
    Propose a file format for output that consolidates the different analyses.
    Create a PDB file of this structure by changing the "Temperature factor or B-factor" field to values based on the physicochemical properties of the residues or their amino acid composition.
    Calculate the contact matrix of the protein and offer to write a file compatible with visualizing this matrix using a graphical tool (Excel, R, or others).

## How to Use:

Start by installing Python on your computer.
Ensure that the Python path is correctly listed in your environment variables.
To properly use our Python program, you must first install 5 packages in the command prompt: Biopython, Matplotlib, Pandas, and Openpyxl.

Open the command prompt and run the following commands:

For Windows:

    pip install matplotlib
    pip install pandas
    pip install openpyxl
    pip install seaborn matplotlib
    pip install biopython

For Mac: Type python3.6 -m pip install [package name] where 3.6 in python3.6 should be replaced by the Python version installed on your computer.

The Biopython package is a specialized Python library for processing and analyzing biological data. It provides a set of modules and tools for working with bioinformatics data, such as the Numpy tool (a library used for numerical computing).
Matplotlib is useful for creating graphs.
The Pandas package is essential because it is a useful library for data manipulation and analysis in Python.
Finally, the Openpyxl package is a Python library used to interact with Excel files. It provides functionalities to create, read, and modify Excel files in the xlsx (Open XML) format. We needed to install it because one module of our program requires the manipulation of tabular data with Excel (for calculating the hydrophobicity profile).

Ensure that you are connected to the internet if you want to search for your PDB file online.
For offline use, ensure that you have downloaded the PDB file of your protein, and that this file is in the program’s directory.

To launch the program, download the program with all the modules and stock it in a directory. Then launch the "main" file to start the analysis.

The program can generate graphs, which you will need to save manually.
A FASTA file can also be created in this directory.
An Excel file with a table of the molecule’s hydrophobicity values can also be created in this directory. From this, you can create a hydrophobicity graph.
A report will be generated in this directory with the essential data from the PDB file analysis.
A new PDB file will be created where the B-factor values will be replaced by the hydrophobicity of the residues according to the following scale:

    'ALA': 16.67,     # Non-aromatic hydrophobic
    'VAL': 16.67,     # Non-aromatic hydrophobic
    'LEU': 16.67,     # Non-aromatic hydrophobic
    'ILE': 16.67,     # Non-aromatic hydrophobic
    'MET': 16.67,     # Non-aromatic hydrophobic
    'PRO': 16.67,     # Non-aromatic hydrophobic

    'PHE': 33.34,     # Aromatic hydrophobic
    'TYR': 33.34,     # Aromatic hydrophobic
    'TRP': 33.34,     # Aromatic hydrophobic

    'SER': 50.01,     # Neutral polar
    'THR': 50.01,     # Neutral polar
    'ASN': 50.01,     # Neutral polar
    'GLN': 50.01,     # Neutral polar
    'CYS': 50.01,     # Neutral polar

    'ASP': 66.67,     # Acidic polar
    'GLU': 66.67,     # Acidic polar

    'LYS': 83.34,     # Basic polar
    'ARG': 83.34,     # Basic polar
    'HIS': 83.34,     # Basic polar

    'CYS': 99.99      # Sulfuric acid

To visualize the protein’s coloring based on its hydrophobicity, open the created PDB file with PyMOL, then type spectrum b in the command prompt.

Happy usage!
