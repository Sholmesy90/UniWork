using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asgn
{
    public class HuffmanGenerator
    {
        List<Node> list;
        Dictionary<char, DAABitArray> codeDict;

        public HuffmanGenerator()
        {
            list = new List<Node>();
        }

        public HuffmanGenerator(List<Node> inList)
        {
            list = inList;
        }

        public Node CreateTree()
        {
            while (list.Count > 1)
            {
                list = list.OrderBy(n => n.GetFreq()).ToList();

                Node n1 = list.First();
                list.RemoveAt(0);
                Node n2 = list.First();
                list.RemoveAt(0);

                Node newNode = new Node();
                newNode.SetFreq(n1.GetFreq() + n2.GetFreq());
                newNode.SetLeft(n1);
                newNode.SetRight(n2);

                list.Add(newNode);
            }
            return list.First();
        }

        public void BuildEncodingMap(Node n)
        {
            Stack<bool> stack = new Stack<bool>();
            BuildRecursive(n, stack);
        }

        public void BuildRecursive(Node n, Stack<bool> stack)
        {
            if (n.IsBranch())
            {
                stack.Push(false);
                BuildRecursive(n.GetLeft(), stack);
                stack.Pop();

                stack.Push(true);
                BuildRecursive(n.GetRight(), stack);
                stack.Pop();
            }
            else
            {
                DAABitArray bitArray;
                foreach (bool b in stack)
                {

                }

            }
        }
    }
}
