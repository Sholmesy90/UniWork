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
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        List <Node> nodeList = new List<Node>();
        Node huffmanTree;
        Dictionary<char, DAABitArray> encodeDict;

        public MainWindow()
        {
            InitializeComponent();
        }

        private void btnFreq_Click(object sender, RoutedEventArgs e)
        {
            FreqListGenerator flg = new FreqListGenerator();
            txtFreqTbl.Text = flg.CreateFreqTable(txtPlain.Text.ToCharArray());
            nodeList = flg.GetFreqList();
            if (nodeList.Count == 0)
            {
                MessageBox.Show("Cannot calculate frequency of a message that" 
                                + " doesn't exist!\nPlease enter (1) or more "
                                + "alphanumeric characters");
            }
        }

        private void btnCompress_Click(object sender, RoutedEventArgs e)
        {
            if (txtPlain.Text.Count() == 0)
            {
                MessageBox.Show("Cannot compress a message that doesn't exist!\n" +
                                "Please enter (1) or more alphanumeric characters");
            }
            else if (nodeList.Count == 0)
            {
                MessageBox.Show("Cannot compress text without a frequency table!\n" +
                                "Please generate a frequency table before compression.");
            }
            else
            {
                HuffmanGenerator hg = new HuffmanGenerator(nodeList);
                huffmanTree = hg.CreateTree();
                encodeDict = hg.BuildEncodingMap(huffmanTree);

                HuffmanEncoder he = new HuffmanEncoder();
                txtCompressed.Text = he.Encode(txtPlain.Text.ToCharArray(), huffmanTree, encodeDict);
                txtPlain.Text = "";
                txtFreqTbl.Text = "";
            }
        }

        private void btnDecompress_Click(object sender, RoutedEventArgs e)
        {
            HuffmanDecoder hd = new HuffmanDecoder();
            hd.Decode(txtCompressed.Text.ToCharArray(), huffmanTree);
        }
    }
}
