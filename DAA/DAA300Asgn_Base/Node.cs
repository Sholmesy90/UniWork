using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asgn
{
    class Node
    {
        Node Left;
        Node Right;
        char Symbol;
        int Frequency;

        public Node()
        {
            Left = null;
            Right = null;
            Frequency = 0;
            Symbol = '!';
        }
        public void SetSymbol(char c)
        {
            Symbol = c;
        }
        public void SetLeft(Node n)
        {
            Left = n;
        }
        public void SetRight(Node n)
        {
            Right = n;
        }
        public void SetFreq(int i)
        {
            Frequency = i;
        }
        public char GetSymbol()
        {
            return Symbol;
        }
        public Node GetLeft()
        {
            return Left;
        }
        public Node GetRight()
        {
            return Right;
        }
        public int GetFreq()
        {
            return Frequency;
        }
    }
}
