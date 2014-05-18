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

        public MainWindow()
        {
            InitializeComponent();
        }

        private void btnCompress_Click(object sender, RoutedEventArgs e)
        {
            if (nodeList.Count == 0)
            {
                MessageBox.Show("Can't compress a message that doesn't exist!\n" +
                                "Please enter (1) or more alphanumeric characters");
            }
            else
            {
                HuffmanGenerator hg = new HuffmanGenerator(nodeList);
                huffmanTree = hg.CreateTree();
                hg.BuildEncodingMap(huffmanTree);
            }
        }

        private void btnFreq_Click(object sender, RoutedEventArgs e)
        {
            FreqListGenerator flg = new FreqListGenerator();
            txtFreqTbl.Text = flg.CreateFreqTable(txtPlain.Text.ToCharArray());
            nodeList = flg.GetFreqList();
            if (nodeList.Count == 0)
            {
                MessageBox.Show("Can't calculate frequency of a message that doesn't exist!\n" +
                                "Please enter (1) or more alphanumeric characters");
            }
        }
    }
}
