package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 従業員データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_USR)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_USR_GET_ALL,
            query = JpaConst.Q_USR_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_USR_GET_BY_CODE_AND_PASS,
            query = JpaConst.Q_USR_GET_BY_CODE_AND_PASS_DEF),
    @NamedQuery(
            name = JpaConst.Q_USR_GET_BY_ID,
            query = JpaConst.Q_USR_GET_BY_ID_DEF)
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class User {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.USR_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * ユーザーID
     */
    @Column(name = JpaConst.USR_COL_USR_ID, nullable = false, unique = true)
    private String userId;

    /**
     * 氏名
     */
    @Column(name = JpaConst.USR_COL_NAME, nullable = false)
    private String name;

    /**
     * パスワード
     */
    @Column(name = JpaConst.USR_COL_PASS, length = 64, nullable = false)
    private String password;

    /**
     *登録日時
     */
    @Column(name = JpaConst.USR_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.USR_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;



}