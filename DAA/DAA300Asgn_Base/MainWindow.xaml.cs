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
        public MainWindow()
        {
            InitializeComponent();
        }

        private void btnCompress_Click(object sender, RoutedEventArgs e)
        {

        }

        private void btnFreq_Click(object sender, RoutedEventArgs e)
        {
            char[] charArray = txtPlain.Text.ToCharArray();
            int[] frequencyArray = new int[64];
            for (int i = 0; i < charArray.Length; i++)
            {
               int number = 0;

               char c = charArray[i];
        
               if ((c >= '0') && (c <= '9'))
                   number = (int)c + 5;
               else if ((c >= 'a') && (c <= 'z'))
                   number = (int)c - 70;
               else if ((c >= 'A') && (c <= 'Z'))
                   number = (int)c - 64;
               else if (c == '\n')
                   number = 63;
               else if (c == ' ')
                   number = 0;

               frequencyArray[number]++;
            }
            printFreq(frequencyArray);
        }

        private void printFreq(int[] array)
        {
            String finalString = "";
            for (int i = 0; i < array.Length; i++)
            {
                if (array[i] != 0)
                {
                    Node newNode = new Node();
                    if (i == 0)
                    {
                        finalString += "space : ";
                        newNode.SetSymbol(' ');
                    }
                    else if ((i >= 1) && (i <= 26))
                    {
                        finalString += (char)(i + 64) + " : ";
                        newNode.SetSymbol((char)(i + 64));
                    }
                    else if ((i >= 27) && (i <= 52))
                    {
                        finalString += (char)(i + 70) + " : ";
                        newNode.SetSymbol((char)(i + 70));
                    }
                    else if ((i >= 53) && (i <= 62))
                    {
                        finalString += (char)(i - 5) + " : ";
                        newNode.SetSymbol((char)(i - 5));
                    }
                    else
                    {
                        finalString += "new line : ";
                        newNode.SetSymbol('\n');
                    }
                    newNode.SetFreq(array[i]);
                    finalString += array[i] + "\n";
                }
            }
            txtFreqTbl.Text = finalString;
        }
    }
}
