package com.anasoft.os.daofusion.criteria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Embedded;

import org.hibernate.Criteria;

/**
 * Association path which points to the given property of the target persistent
 * entity.
 * 
 * <p>
 * 
 * Association path starts at the target persistent entity as the root object,
 * navigating through associated objects as necessary. This way, nested property
 * criteria which operate on associated objects can be easily defined by the
 * user. Note that the association path does not include the target property
 * itself.
 * 
 * <p>
 * 
 * This class is immutable by design so you can safely reuse its instances
 * across the code.
 * 
 * @see AssociationPathElement
 * @see AssociationPathRegister
 * 
 * @author michal.jemala
 * @author vojtech.szocs
 */
public class AssociationPath implements Iterable<AssociationPath> {

	public static final String SEPARATOR = ".";
	public static final String SEPARATOR_REGEX = "\\.";

	private static final String ALIAS_SEPARATOR = "__";
	private static final String EMBEDDED_ALIAS_SEPARATOR = "_";

	/**
	 * Shorthand constant for an empty association path which essentially points
	 * to target criteria root.
	 */
	public static final AssociationPath ROOT = new AssociationPath();

	private final List<AssociationPathElement> elements = new ArrayList<AssociationPathElement>();

	/**
	 * Creates a new association path.
	 * 
	 * @param elements
	 *            Association path elements.
	 */
	public AssociationPath(AssociationPathElement... elements) {
		this(ROOT, elements);
	}

	/**
	 * Creates a new association path with <tt>rootPath</tt> elements placed at
	 * the beginning, followed by <tt>elements</tt> in consequence.
	 * 
	 * @param prefix
	 *            Association path prefix (elements to be placed at the
	 *            beginning of this association path).
	 * @param elements
	 *            Association path elements.
	 */
	public AssociationPath(AssociationPath prefix,
			AssociationPathElement... elements) {
		if (prefix != null)
			this.elements.addAll(prefix.elements);

		if (elements != null) {
			for (AssociationPathElement pathElement : elements)
				this.elements.add(pathElement);
		}
	}

	/**
	 * Returns the last element of this association path or <tt>null</tt> in
	 * case the association path is empty.
	 * 
	 * @return Last element of this association path (can be <tt>null</tt>).
	 */
	public AssociationPathElement getLastElement() {
		if (elements.isEmpty()) {
			return null;
		} else {
			return elements.get(elements.size() - 1);
		}
	}

	/**
	 * Returns the "super path" for this association path.
	 * 
	 * <p>
	 * 
	 * Super path includes all path elements except the last one.
	 * 
	 * @return Super path for this association path.
	 */
	public AssociationPath getSuperPath() {
		AssociationPath superPath = new AssociationPath();

		for (int i = 0; i < elements.size() - 1; i++) {
			superPath.elements.add(this.elements.get(i));
		}

		return superPath;
	}

	/**
	 * Returns the alias for this association path.
	 * 
	 * <p>
	 * 
	 * This method is used by {@link AssociationPathRegister} when creating
	 * {@link Criteria} instances so that these instances can be reused by
	 * referencing their aliases.
	 * 
	 * @return Alias for this association path.
	 */
	public String getAlias() {
		return getNativePath(ALIAS_SEPARATOR, true);
	}

	/**
	 * Returns an iterator over {@link AssociationPath} instances for this
	 * association path.
	 * 
	 * <p>
	 * 
	 * The resulting iterator follows a custom logic based on the order of
	 * contained association path elements. For example, an iterator over the
	 * association path "<tt>a.b.c</tt>" will produce following paths:
	 * 
	 * <ol>
	 * <li><tt>a</tt>
	 * <li><tt>a.b</tt>
	 * <li><tt>a.b.c</tt>
	 * </ol>
	 * 
	 * Note that the {@link Iterator#remove()} operation is not supported.
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<AssociationPath> iterator() {
		final Iterator<AssociationPathElement> internalIterator = elements.iterator();

		return new Iterator<AssociationPath>() {

			private List<AssociationPathElement> elements = new ArrayList<AssociationPathElement>();

			public boolean hasNext() {
				return internalIterator.hasNext();
			}

			public AssociationPath next() {
				AssociationPathElement element = internalIterator.next();
				this.elements.add(element);

				AssociationPath path = new AssociationPath();
				for (int i = 0; i < this.elements.size(); i++) {
					path.elements.add(this.elements.get(i));
				}

				return path;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((elements == null) ? 0 : elements.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		AssociationPath other = (AssociationPath) obj;

		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return getNativePath(SEPARATOR, false);
	}

	/**
	 * Concatenates association path element values using the given
	 * <tt>separator</tt> into a "native path".
	 * <p>
	 * When <tt>{@link Embedded}</tt> objects used within association path all
	 * dots must be replaced to enable aliases to work correctly.
	 * 
	 * @param separator
	 *            Element value separator.
	 * @param replaceEmbedded
	 *            If true all dots will be replaced.
	 * @return Native path of association path elements concatenated with the
	 *         given <tt>separator</tt>.
	 */
	private String getNativePath(String separator, boolean replaceEmbedded) {
		StringBuilder sb = new StringBuilder();

		for (AssociationPathElement pathElement : elements) {
			if (sb.length() > 0)
				sb.append(separator);
			String pathElementValue = pathElement.getValue();
			if (replaceEmbedded)
				pathElementValue = pathElementValue.replaceAll(SEPARATOR_REGEX,
						EMBEDDED_ALIAS_SEPARATOR);
			sb.append(pathElementValue);
		}

		return sb.toString();
	}

}
