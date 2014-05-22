using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Collections;

namespace Asgn
{
    /// This class deals with delegating events created from user input
    /// to the various functions required to perform the tasks. 
    /// The 'business' logic occurs in other classes, as this is 
    /// simply for user interaction and delegation.
    public partial class MainWindow : Window
    {
        List <Node> nodeList = new List<Node>();
        Node huffmanTree;
        Dictionary<char, DAABitArray> encodeDict;

        public MainWindow()
        {
            InitializeComponent();
        }

        /// Takes an input string and calculates the frequency of the
        /// characters in the text. It generates a list of Nodes
        /// for each unique character symbol. This is used in 
        /// compression and decompression, and the list is stored
        /// for later.
        private void BtnFreqClick(object sender, RoutedEventArgs e)
        {
            FreqListGenerator flg = new FreqListGenerator();
            String freqTable = flg.CreateFreqTable(txtPlain.Text.ToCharArray());
            nodeList = flg.GetFreqList();
            if (freqTable.Count() == 0)
            {
                MessageBox.Show("Cannot calculate frequency of a message that"
                                + " doesn't exist!\nPlease enter (1) or more "
                                + "alphanumeric characters");
            }
            else if (!flg.CharIsValid(freqTable[0])) /// Invalid character
            {
                MessageBox.Show("One or more characters is invalid. Please"
                                + " use characters 0-9,\na-z, A-Z, space and"
                                + " new line. Invalid characters : " 
                                + freqTable);          
            }
            else
            {
                txtFreqTbl.Text = freqTable;
            }
        }

        /// Takes an input string and the frequency list generated earlier to
        /// create a Huffman Tree to be used for the compression and
        /// decompression. It then encodes the provided text string and outputs
        /// it to the user in a compressed format, which can later be used
        /// to decompress to verify the result.
        private void BtnCompressClick(object sender, RoutedEventArgs e)
        {
            if (txtPlain.Text.Count() == 0)
            {
                MessageBox.Show("Cannot compress a message that doesn't" 
                                + " exist!\nPlease enter (1) or more " 
                                + "alphanumeric characters");
            }
            else if (nodeList.Count == 0)
            {
                MessageBox.Show("Cannot compress text without a frequency"
                                + " table!\nPlease generate a frequency "
                                + "table before compression");
            }
            else
            {
                HuffmanGenerator hg = new HuffmanGenerator(nodeList);
                huffmanTree = hg.CreateTree();
                encodeDict = hg.BuildEncodingMap(huffmanTree);

                HuffmanEncoder he = new HuffmanEncoder();
                txtCompressed.Text = he.Encode(txtPlain.Text.ToCharArray(), encodeDict);
                txtPlain.Text = "";
                txtFreqTbl.Text = "";
            }
        }

        /// Takes the compressed text and the existing Huffman Tree and 
        /// parses the text through the tree to decode the message back into
        /// it's original format.
        private void BtnDecompressClick(object sender, RoutedEventArgs e)
        {
            if (txtCompressed.Text.Count() == 0)
            {
                MessageBox.Show("Cannot decompress text that doesn't exist!"
                                + "\nPlease compress text before decompressing");
            }
            else if (encodeDict.Count() == 0)
            {
                MessageBox.Show("No frequency table/Huffman tree provided\nPlease "
                                + "generate one by compressing some text before"
                                + " decompressing");
            }
            else
            {
                HuffmanDecoder hd = new HuffmanDecoder();
                txtPlain.Text = hd.Decode(txtCompressed.Text.ToCharArray(),
                                                    huffmanTree);

                FreqListGenerator flg = new FreqListGenerator();
                txtFreqTbl.Text = flg.CreateFreqTable(txtPlain.Text.ToCharArray());
                nodeList = flg.GetFreqList();
                txtCompressed.Text = "";
            }
        }
    }
}
