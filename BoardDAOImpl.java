package com.lec.ex02_board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class BoardDAOImpl implements BoardDAOService {

	private BoardVO inputBoard() {
		BoardVO board = new BoardVO();
		
		String subject = JOptionPane.showInputDialog("글제목을 입력하세요");
		String writer = JOptionPane.showInputDialog("작성자를 입력하세요");
		String content = JOptionPane.showInputDialog("글내용을 입력하세요");
		
		board.setSubject(subject);
		board.setWriter(writer);
		board.setContent(content);
		return board;
	}
	@Override
	public void createBoard() {
		BoardVO board = inputBoard();
		
		ConnectionFactory cf = new ConnectionFactory();
		Connection conn = cf.getConnection();
		PreparedStatement pstmt = null;
		String sql = cf.getInsert();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getSubject());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			int row = pstmt.executeUpdate();
			System.out.println(row+"건이 성공적으로 등록되었습니다");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
			}
		}
		
	}


	@Override
	public ArrayList<BoardVO> listBoard() {
		ArrayList<BoardVO> boardList = new ArrayList<>();
		
		ConnectionFactory cf = new ConnectionFactory();
		Connection conn = cf.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = cf.getSelect()
				   + " where rownum between 1 and 10"
				   + " order by bno desc";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setBno(rs.getInt(1));
				board.setSubject(rs.getString("subject"));
				board.setWriter(rs.getString("writer"));
				board.setContent(rs.getString("content"));
				boardList.add(board);
			}
		} catch (SQLException e) {
			System.out.println("게시글목록 조회 실패");
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
			}
		}
		return boardList;
	}
	

	@Override
	public BoardVO viewBoard(int bno) {
		BoardVO board =new BoardVO();
		
	System.out.println("select * from board where bno =" + bno);
		// select * from board where bno = 10;
		System.out.println("글조회성공");
		return board;
	}

	@Override
	public void updateBoard(int bno) {
		System.out.println("글수정성공");
	}

	@Override
	public void deleteBoard(int bno) {
		System.out.println("글삭제성공");
	}

	@Override
	public ArrayList<BoardVO> findBySubjectBoard(String subject) {
		System.out.println("제목검색성공");
		return null;
	}

	@Override
	public ArrayList<BoardVO> findByWriterBoard(String writer) {
		System.out.println("작성자검색성공");
		return null;
	}

	
}
